import { Cell, Graph, Node } from "@antv/x6";
import React from "react";

export interface Window {
    [propName: string]: any;
}

/*Menu.tsx*/
export type MenuPropsType = {
    top: number;
    left: number;
    graph: Graph;
    node: Node | null;
    show: boolean;
    handleShowMenu: (value: boolean) => void;
};

export enum HorizontalAlignState {
    LEFT = 1,
    CENTER,
    RIGHT,
}

export enum OuterEdgeType {
    INPUT = 'input',
    OUTPUT = "output"
};

export enum VerticalAlignState {
    TOP = 1,
    CENTER,
    BOTTOM,
}

export enum NoteTextColor {
    YELLOW = 1,
    ORANGE,
    RED,
    PURPLE,
    GREEN,
    BLUE,
    GRAY,
    TRANSPARENT,
}

/*left-editor.tsx*/
export interface ParametersConfigType {
    name: string,
    flag: boolean,
    type?: string,
}

export interface ColumnType {
    name: string;
    outName: string;
    desc: string;
    type: string;
}

export type SubGraphCells = {
    [oldCellId: string]: Cell;
};

export type MenuInfo = {
    x: number;
    y: number;
    node: Node;
    showStencilMenu: boolean;
};

/*verifyOperator.ts*/
export interface VerifyOperatorItem {
    color: string;
    edge: null | string[];
    operatorErrorMsg: null | string[];
    operatorId: string;
    portInformation: PortInformation | null;
    sqlErrorMsg: string | null;
    tableName: string;
}

export interface PortInformation {
    [propName: string]: string[];
}

/*init-menu.ts*/
export type DispatchMenuInfo = React.Dispatch<
    React.SetStateAction<{
        show: boolean;
        top: number;
        left: number;
        node: Node | null;
    }>
>;

/*graph-tools-func.ts*/
export interface sourceConfigType {
    name: string;
    type: string;
    desc: string;
    outName: string;
}

export interface TargetInfoType {
    targetNode: Node;
    targetPortId: string
}
export interface SourceInfoType {
    sourceNode: Node;
    sourcePortId: string
}

/*autocomplete-utils.ts*/
export interface configType {
    flag: boolean;
    name?: string;
    type: string;
    decs: string;
    outName?: string;
}

export interface configObjType {
    [propName: string]: configType[];
}

/*custom-svg.tsx*/
export type SvgType = {
    iconPath: string;
    styleObj?: any;
};

/*custom-svg.tsx*/
export type NodeType = {
    node?: any;
};


