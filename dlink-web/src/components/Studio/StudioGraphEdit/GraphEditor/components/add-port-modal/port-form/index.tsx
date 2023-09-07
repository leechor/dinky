import React from 'react';
import { FormInstance } from 'antd/es/form/hooks/useForm';
import { Values } from 'async-validator';
import { Input, Form, Row, Col, Button } from 'antd';
import { Node } from '@antv/x6';
import { MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import { l } from '@/utils/intl';
import { isDuplicateOperator } from '../../../utils/graph-tools-func';
type PortProFormProps = {
  values: Node;
  form: FormInstance<Values>;
};
export const FORM_LAYOUT_PUBLIC = {
  labelCol: { xs: { span: 24 }, sm: { span: 5 } },
  wrapperCol: { xs: { span: 24 }, sm: { span: 20 } },
};
export const FORM_LAYOUT_PUBLIC_LABEL = {
  wrapperCol: { xs: { span: 24, offset: 0 }, sm: { span: 20, offset: 5 } },
};
const PortForm: React.FC<PortProFormProps> = (props) => {
  const { values, form } = props;
  const vallidatePortName = (rule: any, val: string, callback: any) => {
    if (val === '') {
      callback(l("graph.portform.portname.cannot.empty"));
    } else {
      const found = values.getPorts().find((item) => item.id === val);
      if (!found) {
        callback();
      } else {
        callback(l("graph.portform.portname.cannot.repeat"));
      }
    }
  };

  /**
   * construct role form
   * @constructor
   */
  const renderDuplicatePorts = () => {
    return (
      <>
        <Form.Item
          name="portName"
          label="portName"
          rules={[
            { required: true, message: l("graph.portform.portname.cannot.empty") },
            {
              validator: (rule, val, callback) => {
                vallidatePortName(rule, val, callback);
              },
            },
          ]}
        >
          <Input placeholder="input portName..." />
        </Form.Item>
      </>
    );
  };
  const renderCustomerPorts = () => {
    return (
      <>
        <Row>
          <Col span={12}>
            <Form.List name="inputPort">
              {(fields, { add, remove }, { errors }) => (
                <>
                  {fields.map((field, index) => (
                    <Form.Item
                      {...(index === 0 ? FORM_LAYOUT_PUBLIC : FORM_LAYOUT_PUBLIC_LABEL)}
                      label={index === 0 ? 'inputPort' : ''}
                      required={false}
                      key={field.key}
                    >
                      <Form.Item
                        {...field}
                        noStyle
                        validateTrigger={['onChange', 'onBlur']}
                        rules={[
                          {
                            required: true,
                            whitespace: true,
                            message: 'please input portName',
                          },
                          {
                            validator(rule, value, callback) {
                              vallidatePortName(rule, value, callback);
                            },
                          },
                        ]}
                      >
                        <Input placeholder="input portName..." style={{ width: '50%' }}></Input>
                      </Form.Item>
                      {fields.length > 1 ? (
                        <MinusCircleOutlined
                          className="dynamic-delete-button"
                          onClick={() => remove(field.name)}
                        />
                      ) : null}
                    </Form.Item>
                  ))}

                  <Form.Item>
                    <Button
                      type="dashed"
                      onClick={() => add()}
                      style={{ width: '50%' }}
                      icon={<PlusOutlined />}
                    >
                      Add inputPort
                    </Button>
                    <Form.ErrorList errors={errors} />
                  </Form.Item>
                </>
              )}
            </Form.List>
          </Col>
          <Col span={12}>
            <Form.List name="outputPort">
              {(fields, { add, remove }, { errors }) => (
                <>
                  {fields.map((field, index) => (
                    <Form.Item
                      {...(index === 0 ? FORM_LAYOUT_PUBLIC : FORM_LAYOUT_PUBLIC_LABEL)}
                      label={index === 0 ? 'outputPort:' : ''}
                      required={false}
                      key={field.key}
                    >
                      <Form.Item
                        {...field}
                        noStyle
                        validateTrigger={['onChange', 'onBlur']}
                        rules={[
                          {
                            required: true,
                            whitespace: true,
                            message: 'please input portName',
                          },
                          {
                            validator(rule, value, callback) {
                              vallidatePortName(rule, value, callback);
                            },
                          },
                        ]}
                      >
                        <Input placeholder="input portName..." style={{ width: '50%' }}></Input>
                      </Form.Item>
                      {fields.length > 1 ? (
                        <MinusCircleOutlined
                          className="dynamic-delete-button"
                          onClick={() => remove(field.name)}
                        />
                      ) : null}
                    </Form.Item>
                  ))}

                  <Form.Item>
                    <Button
                      type="dashed"
                      onClick={() => add()}
                      style={{ width: '50%' }}
                      icon={<PlusOutlined />}
                    >
                      Add outputPort
                    </Button>
                    <Form.ErrorList errors={errors} />
                  </Form.Item>
                </>
              )}
            </Form.List>
          </Col>
        </Row>
      </>
    );
  };

  /**
   * render
   */
  return (
    <>
      <Form form={form} {...FORM_LAYOUT_PUBLIC_LABEL} preserve={false}>
        {isDuplicateOperator(values)
          ? renderDuplicatePorts()
          : renderCustomerPorts()}
      </Form>
    </>
  );
};
export default PortForm;
