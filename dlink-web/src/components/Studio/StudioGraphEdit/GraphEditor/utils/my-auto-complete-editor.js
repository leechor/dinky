import { StringEditor } from "@json-editor/json-editor/src/editors/string";
import { extend } from "@json-editor/json-editor/src/utilities"
import autoComplete from "@tarekraafat/autocomplete.js/dist/autoComplete";
import helper from "./autocomplete-utils.ts"

export class MyAutoCompleteEditor extends StringEditor {

  build() {

    if (this.options.schema.autoinput.function.hasOwnProperty("args")) {
      this.options.format = 'textarea'
    } else {
      this.options.format = 'text'
    }
    super.build()
    this.input_type = this.schema.format /* Restore original format */
    this.input.setAttribute('data-schemaformat', this.input_type)
    this.input.setAttribute('autocomplete', "off")
  }

  postBuild() {
    super.postBuild();
  }

  afterInputReady() {
    // if(window.autoComplete && !this.autocomplete_instance) {
    /* Get options, either global options from "this.defaults.options.autocomplete" or */
    /* single property options from schema "options.autocomplete" */
    let options = this.expandCallbacks('autoinput', extend({},
      {}, this.defaults.options.autoinput || {}, this.options.autoinput || {}, this.options.schema.autoinput || {}))

    const autoCompleteJS = new autoComplete({
      selector: () => this.input,
      data: {
        src: async () => {
          try {
            const { datas } = await helper[options.function.name](options.function.args)
            return datas
          } catch (error) {
            return null
          }

        },
        filter: (list) => {
          // Filter duplicates
          return Array.from(
            new Set(list.map((value) => value.match))
          ).map((item) => {
            return list.find((value) => value.match === item);
          });
        }
      },
      debounce: 300,
      resultsList: {
        tag: "ul",
        element: (list, data) => {
          list.style = `position: absolute;background: #fff;width: 100%;margin: 0;z-index:999`
        }
      },
      resultItem: {
        element: (item, data) => {
          // Modify Results Item Style
          item.style = "display: flex; justify-content: space-between;";
          // Modify Results Item Content
          item.innerHTML = `<span style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">${data.match}</span>`;
          item.addEventListener("mouseenter", () => {
            item.style.backgroundColor = 'rgb(24, 144, 255)'
          });
          item.addEventListener("mouseleave", () => {
            item.style.backgroundColor = '#fff'
          })
        },
        highlight: true
      },
      query: (query) => {
        // 处理结果为下拉框的输入值
        // Split query into array
        const querySplit = query.match(/\w+/g);
        if (!querySplit) {
          return query;
        }

        const regrex = /\W$/;
        if (regrex.test(query)) {
          return '';
        }

        // Get last query value index
        const lastQuery = querySplit.length - 1;
        // Trim new query
        const newQuery = querySplit[lastQuery].trim();

        return newQuery;
      },
      trigger: (query) => {
        // 判断query是否触发下拉框搜索
        return query.length > 0;
      },
      events: {
        input: {
          selection: (event) => {
            // 支持文本多输入的下拉框
            const feedback = event.detail;
            const input = this.input;
            // Trim selected Value
            const selection = feedback.selection.value.trim();

            const regrex = /(.*\W)\w+$/;
            // Split query into array and trim each value
            const query = regrex.exec(input.value);

            const result = query ? query[1] : '';
            // Replace Input value with the new query
            input.value = result + selection;
          },
          keydown(event) {
            switch (event.keyCode) {
              case 40:
              case 38:
                // Down/Up arrow
                event.preventDefault();
                event.keyCode === 40 ? autoCompleteJS.next() : autoCompleteJS.previous();
                break;
              case 13:
                // Enter
                if (autoCompleteJS.cursor >= 0) {
                  event.preventDefault();
                  autoCompleteJS.select(event);
                }
                break;
            }
          }
        }
      }
    }, options)

    this.autocomplete_instance = autoCompleteJS;
    super.afterInputReady();
  }

  getData() {

  }

  destroy() {
    if (this.autocomplete_instance) {
      this.autocomplete_instance = null
    }
    super.destroy();
  }
}
