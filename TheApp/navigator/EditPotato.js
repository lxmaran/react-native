/**
 * Created by Alex on 11/9/2016.
 */
import React, {Component} from 'react';
import {View, Text, TouchableHighlight, TextInput, AsyncStorage,StyleSheet} from 'react-native';
import GmailIntent from './GmailIntent';
import * as firebase from 'firebase';

export default class EditPotato extends Component {
    constructor(props) {
        super(props);
        this.state = {potato: this.props.potato,
            potatoesRef: firebase.database().ref('/potatoes/' + this.props.potato.key)}
    }

    navFirst() {
        this.props.navigator.push({
            id: 'potato-list'
        });
    }

     async removePotato(){
        await this.state.potatoesRef.remove();
        this.navFirst();
    }

    async updatePotato(){
        await this.state.potatoesRef.update(this.state.potato);
        this.navFirst();
    }

    render() {
        return (
            <View style={{flex:1}}>
                <TouchableHighlight onPress={this.navFirst.bind(this)}>
                    <Text>Go to potato list</Text>
                </TouchableHighlight>
                <Text>{this.state.potato.id} - {this.state.potato.name}</Text>
                <TextInput
                    placeholder='New Potato Name'
                    onChangeText={(text) => {
                        this.state.potato.name = text
                    }}/>
                <TouchableHighlight onPress={this.updatePotato.bind(this)}>
                    <Text>Update potato</Text>
                </TouchableHighlight>
                <TouchableHighlight onPress={this.removePotato.bind(this)}>
                    <Text>Remove This potato</Text>
                </TouchableHighlight>
                <GmailIntent potatoName={this.state.potato.name}/>
               
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'white',
    },
    chart: {
        width: 200,
        height: 200,
    },
});