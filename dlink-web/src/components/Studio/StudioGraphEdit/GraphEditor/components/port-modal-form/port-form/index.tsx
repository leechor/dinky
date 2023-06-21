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
import { FormInstance } from "antd/es/form/hooks/useForm";
import { Values } from "async-validator";
import { Checkbox, Form, Row, Col } from "antd"
import { ParametersConfigType } from "../../../views/home/cpns/left-editor"
type PortProFormProps = {
    values: ParametersConfigType[];
    form: FormInstance<Values>;
    initValue:string[]
};
export const FORM_LAYOUT_PUBLIC = {
    labelCol: { span: 5 },
    wrapperCol: { span: 15 },
};
const PortForm: React.FC<PortProFormProps> = (props) => {

    const { values, form ,initValue} = props;
    console.log(initValue,"initValue");


    /**
     * construct role form
     * @constructor
     */
    const renderRoleForm = () => {
        return <>
            <Form.Item name="parameters" initialValue={initValue}>
                <Checkbox.Group>{values && values.map(value =>
                    <Checkbox key={value.name} value={value.name} style={{ lineHeight: "32px" }}>{value.name}</Checkbox>
                )}</Checkbox.Group>
            </Form.Item>

        </>
    };

    /**
     * render
     */
    return <>
        <Form
            {...FORM_LAYOUT_PUBLIC}
            form={form}
            layout={'horizontal'}
            preserve={false}
        >

            {renderRoleForm()}
        </Form>
    </>
};
export default PortForm;
