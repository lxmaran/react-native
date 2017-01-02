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
                initialRoute={{id: 'first'}}
                renderScene={this.navigatorRenderScene} />
                );
    }

    navigatorRenderScene(route, navigator) {
        _navigator = navigator;
        switch (route.id) {
            case 'first':
                return (<PotatoList navigator={navigator} title="first"/>);
            case 'second':
                return (<Login navigator={navigator} title="second" />);
            case 'edit' :
                return (<EditPotato navigator={navigator} potato={route.potato} title="edit"/>)
        }
    }
}