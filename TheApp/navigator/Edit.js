/**
 * Created by Alex on 11/9/2016.
 */
import React, {Component} from 'react';
import {View, Text, TouchableHighlight, TextInput} from 'react-native';
import GmailIntent from './GmailIntent';
export default class Edit extends Component {
    constructor(props) {
        super(props);
        this.state = {potato: this.props.potato}
    }

    navFirst() {
        this.props.navigator.push({
            id: 'first'
        });
    }

    render() {
        return (
            <View>
                <TouchableHighlight onPress={this.navFirst.bind(this)}>
                    <Text>Go to potato list</Text>
                </TouchableHighlight>
                <Text>{this.state.potato.id} - {this.state.potato.name}</Text>
                <TextInput
                    placeholder='New Potato Name'
                    onChangeText={(text) => {
                        this.state.potato.name = text
                    }}/>
                <TouchableHighlight onPress={() => console.log(this.state.potato)}>
                    <Text>Update potato</Text>
                </TouchableHighlight>
                <GmailIntent/>
            </View>
        );
    }
}