/**
 * Created by Alex on 11/8/2016.
 */
import React, {Component} from 'react';
import {Navigator} from 'react-native'
import First from './PotatoList';
import Second from './Login'
import Edit from './EditPotato';
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
                return (<First navigator={navigator} title="first"/>);
            case 'second':
                return (<Second navigator={navigator} title="second" />);
            case 'edit' :
                return (<Edit navigator={navigator} potato={route.potato} title="edit"/>)
        }
    }
}