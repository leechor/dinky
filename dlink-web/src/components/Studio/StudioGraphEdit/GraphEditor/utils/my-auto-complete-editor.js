import {StringEditor} from "@json-editor/json-editor/src/editors/string";
import { extend } from "@json-editor/json-editor/src/utilities"
import autoComplete from "@tarekraafat/autocomplete.js/dist/autoComplete";


export class MyAutoCompleteEditor extends StringEditor {

  build () {
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
    let options = this.expandCallbacks('autoinput', extend({}, {
        search: (jsEditor) => {
          // eslint-disable-next-line no-console
          console.log(`No "search" callback defined for autocomplete in property "${jsEditor.key}"`)
          return []
        },
      }, this.defaults.options.auto || {}, this.options.auto || {}))

    this.input.style.display = 'none'
    this.autocomplete_instance = new autoComplete(options)
      this.input = this.autocomplete_instance.input
    // }
    /* Listen for changes */
    this.autocomplete_instance.addEvents('change', () => {
      this.input.value = this.autocomplete_instance.
      this.refreshValue()
      this.is_dirty = true
      this.onChange(true)
    })
    super.afterInputReady();
  }

  destroy() {
    if (this.autocomplete_instance) {
      this.autocomplete_instance = null
    }
    super.destroy();
  }
}
