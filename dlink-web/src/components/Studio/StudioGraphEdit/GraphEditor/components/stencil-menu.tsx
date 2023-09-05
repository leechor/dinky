import { Menu } from '@antv/x6-react-components';
import { memo } from 'react';
import { message } from 'antd';
import '@antv/x6-react-components/es/menu/style/index.css';
import { DeleteOutlined, EditOutlined } from '@ant-design/icons';

import {
  useAppDispatch,
  useAppSelector,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';
import {
  changeStencilMenuInfo,
  changeGroupNameInfo,
  initFlowDataAction,
} from '../store/modules/home';
import { deleteCustomGroupInfo } from '@/components/Common/crud';
import { warningTip } from '../views/home/cpns/left-editor';
import { l } from '@/utils/intl';
export const StencilMenu = memo(() => {
  const { stencilMenuInfo } = useAppSelector((state) => ({
    stencilMenuInfo: state.home.stencilMenuInfo,
  }));
  const { x, y, node } = stencilMenuInfo;
  const dispatch = useAppDispatch();
  const { Item: MenuItem } = Menu;
  const [messageApi, contextHolder] = message.useMessage();
  const onMenuClick = (name: string) => {
    messageApi.info(`clicked ${name}`);

    switch (name) {
      case 'Delete':
        console.log('delete');
        deleteCustomGroupInfo(`/api/zdpx/customer/delete/${node.prop().name}`).then((res) => {
          warningTip(res.code, res.msg);
        });
        dispatch(initFlowDataAction());
        dispatch(changeStencilMenuInfo({ x: 0, y: 0, showStencilMenu: false, node: null }));
        break;
      case 'Change Name':
        dispatch(changeGroupNameInfo({ node, isShowGroupNameModal: true, type: 'CHANGE' }));
        dispatch(changeStencilMenuInfo({ x: 0, y: 0, showStencilMenu: false, node: null }));
        break;
    }
  };

  const blankMenu = () => {
    return (
      <Menu hasIcon={true} onClick={onMenuClick}>
        <MenuItem name="Change Name" icon={<EditOutlined />} text={l('graph.stencilmenu.change.name')} />
        <MenuItem name="Delete" icon={<DeleteOutlined />} text={l('graph.stencilmenu.delete')} />
      </Menu>
    );
  };

  const styleObj: any = {
    position: 'absolute',
    top: `${y}px`, // 将top属性设置为state变量的值
    left: `${x}px`,
    width: '80px',
    height: '105px',
    zIndex: 9999,
  };

  return (
    <div style={styleObj}>
      {contextHolder}
      {blankMenu()}
    </div>
  );
});
