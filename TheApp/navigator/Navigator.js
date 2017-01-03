/**
 * Created by Alex on 11/8/2016.
 */
import React, {Component} from 'react';
import {Navigator} from 'react-native'
import PotatoList from './PotatoList';
import Login from './Login'
import EditPotato from './EditPotato';
export default class Navigation extends Component{
    render() {
        return (
            <Navigator
                initialRoute={{id: 'login'}}
                renderScene={this.navigatorRenderScene} />
                );
    }

    navigatorRenderScene(route, navigator) {
        let _navigator = navigator;
        switch (route.id) {
            case 'potato-list':
                return (<PotatoList navigator={_navigator} title="potato-list"/>);
            case 'login':
                return (<Login navigator={_navigator} title="login" />);
            case 'edit' :
                return (<EditPotato navigator={_navigator} potato={route.potato} title="edit"/>)
        }
    }
}