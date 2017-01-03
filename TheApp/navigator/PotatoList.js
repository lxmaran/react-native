/**
 * Created by Alex on 11/8/2016.
 */
import React, {Component} from 'react';
import {
    View,
    Text,
    TouchableHighlight,
    StyleSheet,
    ListView,
    TextInput,
    Image,
    ScrollView,
    AppRegistry,
    NetInfo,
    Alert,
    AppState
} from 'react-native';
import * as firebase from 'firebase';
import PushNotification from 'react-native-push-notification';
import PushNotificationController from './PushNotification';

var config = {
    apiKey: "AIzaSyCGH_0sLgOAOhiZGdo_DIOhQr8xqOPu6sY",
    authDomain: "potatodb-e887c.firebaseapp.com",
    databaseURL: "https://potatodb-e887c.firebaseio.com",
    storageBucket: "potatodb-e887c.appspot.com",
    messagingSenderId: "690043872519"
  };

firebase.initializeApp(config);


export default class PotatoList extends Component {

    constructor(props) {
        super(props);
        const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this.state = {
            dataSource: ds.cloneWithRows([{}]),
            potatoName: '',
            potatoId: '',
            potatoesRef: firebase.database().ref('/potatoes')
        };

        this.handleAppStateChange = this.handleAppStateChange.bind(this);
        this.fetchData = this.fetchData.bind(this);
    }

    componentDidMount(){
        AppState.addEventListener('change', this.handleAppStateChange);
    }

    componentWillUnmount(){
        AppState.removeListener('change', this.handleAppStateChange);
    }

    handleAppStateChange(appState){
        if(appState === 'background'){
            // this.state.potatoesRef.on('child_added', snapshot => {
            //     console.log("NEW ADD");
            //     PushNotification.localNotificationSchedule({
            //         message: "A potato was added", // (required)
            //         date: new Date(Date.now())
            //     });
            // });
            this.state.potatoesRef.on('child_changed', snapshot => {
                console.log("NEW CHANGED");
                PushNotification.localNotificationSchedule({
                    message: "A potato was changed", // (required)
                    date: new Date(Date.now())
                });
            });
            this.state.potatoesRef.on('child_removed', snapshot => {
                console.log("NEW REMOVED");
                PushNotification.localNotificationSchedule({
                    message: "A potato was removed", // (required)
                    date: new Date(Date.now())
                });
            });

        }
    }

    async fetchData() {
        const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        let data = [];

        await this.state.potatoesRef.once('value', function (snapshot) {
            snapshot.forEach(function (childSnapshot) {
                let childData = childSnapshot.val();
                childData.keyString = childSnapshot.key;
                data.push(childData);
            });
        });

        this.setState({
            dataSource: ds.cloneWithRows(data)
        });
    }

    componentWillMount() {
        this.fetchData().done();
    }

    navSecond() {
        this.props.navigator.push({
            id: 'login'
        })
    }

    async addPotato() {
        if (this.state.potatoName === '' || this.state.potatoId === '' || !this.state.potatoName || !this.state.potatoId) {
            return;
        }
        let newPostRef = this.state.potatoesRef.push();
        newPostRef.set({name: this.state.potatoName, id: this.state.potatoId});

        this.fetchData();
        this.state.potatoId = '';
        this.state.potatoName = '';
    }

    render() {
        return (
            <View style={styles.container}>

                <TouchableHighlight onPress={this.navSecond.bind(this)}>
                    <Text>Go to LogIn</Text>
                </TouchableHighlight>
                <ScrollView>
                    <ListView dataSource={this.state.dataSource}
                              renderRow={(data) => <Row navigator={this.props.navigator}  {...data} />}/>
                </ScrollView>
                <TextInput
                    placeholder='New Potato Name'
                    value={this.state.potatoName}
                    onChangeText={(text) => {
                        this.setState({potatoName: text})
                    }}/>
                <TextInput
                    placeholder='New potato id'
                    value={this.state.potatoId}
                    onChangeText={(text) => {
                        this.setState({potatoId: text})
                }}/>
                <TouchableHighlight onPress={this.addPotato.bind(this)}>
                    <Text>Add potato</Text>
                </TouchableHighlight>
                <PushNotificationController/>
            </View>
        );
    }
}

export class Row extends Component {
    constructor(props) {
        super(props);
        console.log(this.props.keyString);
    }

    navEdit() {
        this.props.navigator.push({
            id: 'edit',
            potato: {id: this.props.id, name: this.props.name, key: this.props.keyString}
        })
    }


    render() {
        return (
            <View style={{flex: 1, flexDirection: 'row'}}>
                <TouchableHighlight onPress={this.navEdit.bind(this)}>
                    <Text>{this.props.id} - {this.props.name}</Text>
                </TouchableHighlight>
            </View>
        );
    }

}

//Styles
const styles = StyleSheet.create({
    container: {
        top: 10,
        padding: 5
    }
});