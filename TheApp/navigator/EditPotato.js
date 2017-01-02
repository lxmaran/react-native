/**
 * Created by Alex on 11/9/2016.
 */
import React, {Component} from 'react';
import {View, Text, TouchableHighlight, TextInput, AsyncStorage,StyleSheet} from 'react-native';
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

     async removePotato(){
        var potatoes = await AsyncStorage.getItem("potatoes");
        var updated = JSON.parse(potatoes)
        var updated2 = updated.filter((i) => {
	        return i.id !== this.state.potato.id;
        });
        AsyncStorage.setItem("potatoes", JSON.stringify(updated2), ()=>{ })
        this.navFirst();
    }

    async updatePotato(){
        var potatoes = await AsyncStorage.getItem("potatoes");
        var updated = JSON.parse(potatoes)
        updated.forEach((i) => {
            if(i.id === this.state.potato.id){
                i.name = this.state.potato.name;
            }
        });
        AsyncStorage.setItem("potatoes", JSON.stringify(updated), ()=>{ })
        this.navFirst();
    }

    render() {
        return (
            <View style={{felx:1}}>
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
const data = [[
    [0, 1],
    [1, 3],
    [3, 7],
    [4, 9],
]];