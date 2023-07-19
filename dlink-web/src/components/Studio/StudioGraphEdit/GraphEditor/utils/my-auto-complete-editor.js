import {StringEditor} from "@json-editor/json-editor/src/editors/string";
import {extend} from "@json-editor/json-editor/src/utilities"
import autoComplete from "@tarekraafat/autocomplete.js/dist/autoComplete";


export class MyAutoCompleteEditor extends StringEditor {

  build() {
    this.options.format = 'textarea' /* Force format into "textarea" */
    super.build()
    this.input_type = this.schema.format /* Restore original format */
    this.input.setAttribute('data-schemaformat', this.input_type)
  }

  postBuild() {
    super.postBuild();
  }

  afterInputReady() {
    // if(window.autoComplete && !this.autocomplete_instance) {
    /* Get options, either global options from "this.defaults.options.autocomplete" or */
    /* single property options from schema "options.autocomplete" */
    let options = this.expandCallbacks('autoinput', extend({}, {}, this.defaults.options.autoinput || {}, this.options.autoinput || {}))

    const autoCompleteJS = new autoComplete({
      options,
      selector: () => this.input,
      data: {
        src: ["abc", "def", "xza"],
        cache: true,
      },
      resultItem: {
        element: (item, data) => {
          // Modify Results Item Style
          item.style = "display: flex; justify-content: space-between;";
          // Modify Results Item Content
          item.innerHTML = `
      <span style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">
        ${data.match}
      </span>
      `;
        },
        highlight: true
      },
      query: (query) => {
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
        return query.length > 0;
      },
      events: {
        input: {
          selection: (event) => {
            const feedback = event.detail;
            const input = this.input;
            // Trim selected Value
            const selection = feedback.selection.value.trim();

            const regrex = /(.+\W)\w+$/;
            // Split query into array and trim each value
            const query = regrex.exec(input.value);

            const result = query ? query[1] : '';
            // Replace Input value with the new query
            input.value = result + selection;
          },
          keydown(event) {
            switch (event.keyCode) {
              // Down/Up arrow
              case 40:
              case 38:
                event.preventDefault();
                event.keyCode === 40 ? autoCompleteJS.next() : autoCompleteJS.previous();
                break;
              // Enter
              case 13:
                if (autoCompleteJS.cursor >= 0) {
                  event.preventDefault();
                  autoCompleteJS.select(event);
                }
                break;
            }
          }
        }
      }
    })

    this.autocomplete_instance = autoCompleteJS;
    super.afterInputReady();
  }

  destroy() {
    if (this.autocomplete_instance) {
      this.autocomplete_instance = null
    }
    super.destroy();
  }
}
