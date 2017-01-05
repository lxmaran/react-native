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

const config = {
    apiKey: "-",
    authDomain: "-",
    databaseURL: "-",
    storageBucket: "-",
    messagingSenderId: "-"
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
            potatoesRef: firebase.database().ref('/potatoes'),
            arrayPotatoes: [],
            currentAppState: AppState.currentState
        };

        this._handleAppStateChange = this._handleAppStateChange.bind(this);
    }

    componentDidMount() {
        AppState.addEventListener('change', this._handleAppStateChange);

        this.state.potatoesRef.on('child_added', snapshot => {
            this.addPotatoSnapshot(snapshot);
            this.updateListViewDataSource(this.state.arrayPotatoes);

            if (this.state.currentAppState === 'background') {
                PushNotification.localNotificationSchedule({
                    message: "A potato was added",
                    date: new Date(Date.now())
                });
            }
        });
        this.state.potatoesRef.on('child_changed', snapshot => {
            this.updatePotatoSnapshot(snapshot);
            this.updateListViewDataSource(this.state.arrayPotatoes);

            if (this.state.currentAppState === 'background') {
                PushNotification.localNotificationSchedule({
                    message: "A potato was changed",
                    date: new Date(Date.now())
                });
            }
        });
        this.state.potatoesRef.on('child_removed', snapshot => {
            this.removePotatoSnapshot(snapshot);
            this.updateListViewDataSource(this.state.arrayPotatoes);
            if (this.state.currentAppState === 'background') {
                PushNotification.localNotificationSchedule({
                    message: "A potato was removed", // (required)
                    date: new Date(Date.now())
                });
            }
        });
        this.state.potatoesRef.on('child_moved', snapshot => {
        });
    }

    updateListViewDataSource(data) {
        const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this.setState({
            dataSource: ds.cloneWithRows(data)
        });
    }

    addPotatoSnapshot(snapshot) {
        let potato = snapshot.val();
        potato.keyString = snapshot.key;
        this.state.arrayPotatoes.push(potato);
    }

    removePotatoSnapshot(snapshot) {
        this.state.arrayPotatoes = this.state.arrayPotatoes.filter(potato => potato.keyString === snapshot.key);
    }

    updatePotatoSnapshot(snapshot) {
        for (let i = 0; i < this.state.arrayPotatoes.length; i++) {
            if (this.state.arrayPotatoes[i].keyString === snapshot.key) {
                this.state.arrayPotatoes[i] = snapshot.val();
                this.state.arrayPotatoes[i].keyString = snapshot.key;
            }
        }
    }

    componentWillUnmount() {
        AppState.removeEventListener('change', this._handleAppStateChange)
    }

    _handleAppStateChange(currentAppState) {
        this.setState({currentAppState: currentAppState});
    }

    navSecond() {
        this.props.navigator.push({
            id: 'login'
        })
    }

    async addPotato() {
        if (this.state.potatoName === '' || this.state.potatoId === '' || !this.state.potatoName || !this.state.potatoId) return;

        let newPostRef = this.state.potatoesRef.push();
        newPostRef.set({name: this.state.potatoName, id: this.state.potatoId});

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
                    placeholder='New potato id'
                    value={this.state.potatoId}
                    onChangeText={(text) => {
                        this.setState({potatoId: text})
                }}/>
                <TextInput
                    placeholder='New Potato Name'
                    value={this.state.potatoName}
                    onChangeText={(text) => {
                        this.setState({potatoName: text})
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