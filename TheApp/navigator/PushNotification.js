/**
 * Created by Alex on 1/3/2017.
 */
import React, {Component} from 'react';
import PushNotification from 'react-native-push-notification';

export default class PushNotificationController extends Component{
    constructor(props){
        super(props);
        PushNotification.configure({
            onNotification : function (notification) {
                console.log('NOTIFICATION', notification);
            },
        });
    }
    render(){
        return null;
    }
}