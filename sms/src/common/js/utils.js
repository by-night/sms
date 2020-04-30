import ElementUI, {
    Message
} from 'element-ui'
import system from './fill'
let util = function (Vue) {
    Vue.prototype.getLink = (h, text, click) => {
      return h('el-link', {
        props: {
          underline: false
        },
        on: {
          click: () => {
            click()
          }
        },
      }, text)
    };
    Vue.prototype.getOpBtn = (h, text, type, click, disabled, directives,color) => {
      //return h(" 定义的元素 "，{ 元素的性质 }，" 元素的内容"/[元素的内容])
      return h('el-button', {
        props: {
          type: type,
          size: 'small'
        },
        attrs: {
          disabled: disabled,
        },
        on: {
          click: () => {
            click()
          }
        },
        directives: directives,
        style: {
          color: color
        }
      }, text)
    };

    Vue.prototype.dealTotal = system.dealTotal;

    function empty (value) {
      if (typeof value === 'boolean') {
        return value
      }
      const flag = value === '' || value === undefined || value === null;
      return !flag;
    }
    Vue.prototype.getSelect = (h, value, input, arr, size, multiple, change, attrs, clearable, disabled) => {
      return h('el-select', {
        props: {
          value: value,
          multiple: empty(multiple),
          disabled: empty(disabled),
          clearable: empty(clearable),
          size: size || ''
        },
        on: {
          input: (value) => {
            input(value);
          },
          change: (value) => {
            if (change) {
              change(value);
            }

          }
        },
        attrs: attrs
      }, [
        arr.map(item => {
          return h("el-option", {
            props: {
              value: item.value,
              label: item.label,
              disabled: item.disabled === undefined ? false : item.disabled
            }
          });
        })
      ])
    };
    // 为了实现Class的私有属性
    const showMessage = Symbol('showMessage')
    /**
     *  重写ElementUI的Message
     *  single默认值true，因为项目需求，默认只弹出一个，可以根据实际需要设置
     */
    class DonMessage {
        success (options, single = true) {
            this[showMessage]('success', options, single)
        }
        warning (options, single = true) {
            this[showMessage]('warning', options, single)
        }
        info (options, single = true) {
            this[showMessage]('info', options, single)
        }
        error (options, single = true) {
            this[showMessage]('error', options, single)
        }

        [showMessage] (type, options, single) {
            if (single) {
                // 判断是否已存在Message
                if (document.getElementsByClassName('el-message').length === 0) {
                    Message[type](options)
                }
            } else {
                Message[type](options)
            }
        }
    }
    Vue.use(ElementUI, {
        size: 'small'
    });
    // 命名根据需要
    Vue.prototype.$message = new DonMessage();
};


export default util
