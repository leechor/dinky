import {StringEditor} from "@json-editor/json-editor/src/editors/string";

export class MyMonacoEditor extends StringEditor {

  setValue(value, initial, fromTemplate) {
    return super.setValue(value, initial, fromTemplate);
  }

  build() {
    this.options.format = 'textarea' /* Force format into "textarea" */
    super.build()
    this.input_type = this.schema.format /* Restore original format */
    this.input.setAttribute('data-schemaformat', this.input_type)
  }

  afterInputReady() {
    super.afterInputReady();
  }

  getNumColumns() {
    return 6;
  }

  enable() {
    super.enable();
  }

  disable(alwaysDisabled) {
    super.disable(alwaysDisabled);
  }

  destroy() {
    super.destroy();
  }
}
