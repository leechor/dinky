export let textSchema = {
  type: 'object',
  title: 'custom-text-node',
  required: [
    'fontSize',
    'fontColor',
    'backgroundColor',
    'fontFamily',
    'fontWeight',
    'horizontalAlign',
    'verticalAlign',
    'tags',
    'parameters',
    'possibleFonts',
  ],
  properties: {
    fontSize: {
      format: 'select2',
      type: 'integer',
      enum: [10, 11, 12, 14, 16, 18, 20, 22, 24, 36, 48, 60, 72, 100],
      default: 24,
      options: {
        // Override defaullt options
        select2: {
          width: 'off',
        },
      },
    },
    fontColor: {
      type: 'string',
      format: 'color',
      title: 'fontColor',
      default: '#666666',
    },
    backgroundColor: {
      type: 'string',
      format: 'color',
      title: 'backgroundColor',
      default: '#efdbff',
    },
    fontFamily: {
      type: 'string',
      format: 'select2',
      enumSource: 'possible_fonts',
      watch: {
        possible_fonts: 'root.possibleFonts',
      },
    },
    fontWeight: {
      type: 'string',
      format: 'select2',
      enum: ['normal', 'bold', 'bolder', 'lighter'],
      options: {
        enum_titles: ['Normal (400)', 'Bold (700)', 'Bolder (900)', 'Lighter (200)'],
      },
    },
    horizontalAlign: {
      type: 'string',
      format: 'select2',
      enum: ['left', 'right', 'center'],
      options: {
        enum_titles: ['left', 'right', 'center'],
      },
    },
    verticalAlign: {
      type: 'string',
      format: 'select2',
      enum: ['top', 'bottom', 'center'],
      options: {
        enum_titles: ['top', 'bottom', 'center'],
      },
    },
    tags: {
      type: 'array',
      format: 'select2',
      uniqueItems: true,
      items: {
        type: 'string',
        enum: ['bold', 'italic', 'smallcaps'],
      },
    },
    parameters: {
      type: 'string',
      default: '',
    },
    possibleFonts: {
      type: 'array',
      format: 'table',
      items: {
        type: 'string',
      },
      default: ['Arial', 'Times', 'Helvetica', 'Comic Sans'],
      options: {
        collapsed: true,
      },
    },
  },
};
