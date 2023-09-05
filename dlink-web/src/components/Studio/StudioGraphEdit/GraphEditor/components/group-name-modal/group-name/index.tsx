/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

import React from 'react';
import { FormInstance } from 'antd/es/form/hooks/useForm';
import { Values } from 'async-validator';
import { Input, Form } from 'antd';
import { Node } from '@antv/x6';
import { l } from '@/utils/intl';
type PortProFormProps = {
  values: Node;
  form: FormInstance<Values>;
};
export const FORM_LAYOUT_PUBLIC = {
  labelCol: { span: 5 },
  wrapperCol: { span: 15 },
};
const GroupName: React.FC<PortProFormProps> = (props) => {
  const { form } = props;
  const vallidateGroupName = (rule: any, val: string, callback: any) => {
    if (val === '') {
      callback(l("graph.groupname.groupname.cannot.empty"));
    } else {
      callback();
    }
  };

  /**
   * construct role form
   * @constructor
   */
  const renderRoleForm = () => {
    return (
      <>
        <Form.Item
          name="groupName"
          label="groupName"
          rules={[
            { required: true, message: l("graph.groupname.groupname.cannot.empty") },
            {
              validator: (rule, val, callback) => {
                vallidateGroupName(rule, val, callback);
              },
            },
          ]}
        >
          <Input placeholder="input groupName..." />
        </Form.Item>
      </>
    );
  };

  /**
   * render
   */
  return (
    <>
      <Form {...FORM_LAYOUT_PUBLIC} form={form} layout={'horizontal'} preserve={false}>
        {renderRoleForm()}
      </Form>
    </>
  );
};
export default GroupName;
