import { autoPromptFunction } from '@/components/Common/crud';
import { Node } from '@antv/x6';
import store from '../store';
import { isSourceDataType } from './graph-tools-func';
interface configType {
  flag: boolean,
  name?: string,
  type: string,
  decs: string,
  outName?: string,
}
interface configObjType {
  [propName: string]: configType[]
}
const helper = {
  getFieldsByFunction: () => {
    return new Promise((resolve, reject) => {
      autoPromptFunction("/api/zdpx/getFillAllByVersion").then(res => {
        let { code, datas } = res
        if (code === 0) {
          resolve({ datas })
        } else {
          resolve({ datas: null })
        }
      }).catch(err => {
        reject(err)
      })
    })
  },
  getFieldsByConfig: () => {
    return new Promise((resolve, reject) => {
      const node = store.getState().home.currentSelectNode
      if (node instanceof Node) {
        const autoPromptName: string[] = []
        if (isSourceDataType(node)) {
          if (node.getData().hasOwnProperty("parameters")) {
            if (node.getData().parameters.output.columns.length !== 0) {
              const config = node.getData().parameters.output.columns;

              config.forEach((element: configType) => {
                if (element.outName) {
                  autoPromptName.push(element.outName)
                } else {
                  autoPromptName.push(element.name!)
                }
              });
              resolve({ datas: autoPromptName })
            } else {
              resolve({ datas: null })
            }
          } else {
            resolve({ datas: null })
          }
        } else {
          if (node.getData().hasOwnProperty("config")) {
            const config = node.getData().config;
            config.forEach((configObj: configObjType) => {
              Object.values(configObj).forEach((item: configType[]) => {
                item.forEach(element => {
                  if (element.outName) {
                    autoPromptName.push(element.outName)
                  } else {
                    autoPromptName.push(element.name!)
                  }
                });
              })
            });
            resolve({ datas: autoPromptName })
          } else {
            resolve({ datas: null })
          }
        }

      } else {
        resolve({ datas: null })
      }
    })
  },
}

export default helper
