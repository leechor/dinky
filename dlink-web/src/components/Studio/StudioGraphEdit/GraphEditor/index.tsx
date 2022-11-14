import React from 'react';
import LogicFlow from "@logicflow/core";
import {Button} from 'antd';
import {BpmnElement, BpmnXmlAdapter, Control, Menu, SelectionSelect, Snapshot} from "@logicflow/extension";
import BpmnPattern from "./pattern";
import BpmnIo from "./io";
import './index.css';
import 'antd/lib/button/style/index.css';
import '@logicflow/extension/lib/style/index.css';

const config = {
    stopScrollGraph: true,
    stopZoomGraph: true,
    metaKeyMultipleSelected: true,
    grid: {
        size: 10,
        type: 'dot',
    },
    keyboard: {
        enabled: true,
    },
    snapline: true,
}


type IState = {
    rendered: boolean,
}

type IProps = {}

export default class GraphEditor extends React.Component<IProps, IState> {
    lf: LogicFlow;

    constructor(props: {} | Readonly<{}>) {
        super(props);
        this.state = {
            rendered: true,
        };
    }

    componentDidMount() {
        LogicFlow.use(BpmnElement);
        LogicFlow.use(BpmnXmlAdapter);
        LogicFlow.use(Snapshot);
        LogicFlow.use(Control);
        LogicFlow.use(Menu);
        LogicFlow.use(SelectionSelect);
        const lf = new LogicFlow({
            ...config,
            container: document.querySelector('#graph') as HTMLElement,
            width: 1000,
            height : 1000,
        });
        lf.render()
        this.lf = lf;
        this.setState({
            rendered: true,
        });
    }

    render() {
        const {rendered} = this.state;
        let tools;
        if (rendered) {
            tools = (
                <div>
                    <BpmnPattern lf={this.lf}/>
                    <BpmnIo lf={this.lf}/>
                </div>
            );
        }
        return (
            <>
                <div className="bpmn-example-container">
                    <div id="graph" className="viewport" style={{width: this.props.width, height: this.props.height}}></div>
                    {tools}
                </div>
            </>
        )
    }
}
