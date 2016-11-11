/**
 * Created by Alex on 11/8/2016.
 */
import React, {Component} from 'react';
import {View, Text, StyleSheet, TouchableHighlight, TextInput} from 'react-native';
import {Buffer} from 'buffer';
export default class Second extends Component{
    constructor(props){
        super(props);
    }
    navFirst(){
        this.props.navigator.push({
            id: 'first'
        })
    }

    onLogInPress() {
        var encoded = new Buffer(this.state.username + ':' + this.state.password);
        var encodedAuth = encoded.toString('base64');
        console.log(encodedAuth);
        fetch('https://api.github.com/user', {
            headers: {
                'Authorization': 'Basic ' + encodedAuth
            }
        })
            .then((response) => response.json())
            .then ((response) => console.log(response))
    }

    render() {
        return (
            <View style={styles.container}>
                <TouchableHighlight onPress={this.navFirst.bind(this)}>
                    <Text>Go to potato list</Text>
                </TouchableHighlight>

                <TextInput
                    placeholder="Give email"
                    onChangeText={(text) => this.setState({username: text})}/>

                <TextInput
                    secureTextEntry={true}
                    onChangeText={(text) => this.setState({password: text})}
                    placeholder="Give password"/>

                <TouchableHighlight
                    style={styles.button}
                    onPress={this.onLogInPress.bind(this)}>
                    <Text style={styles.buttonText}>Log In</Text>
                </TouchableHighlight>
            </View>
        );
    }
};

//Styles
const styles = StyleSheet.create({
    container: {
        top: 10,
        padding: 5,
    },
    input: {
        margin: 1,
        padding: 5,
        borderColor: '#48BBEC',
        borderWidth: 1,
        borderRadius: 5
    },
    button: {
        alignSelf: 'stretch',
        top: 10,
        padding: 5,
        margin: 1,
        backgroundColor: '#48BBEC',
        justifyContent: 'center',
        borderRadius: 5
    },
    buttonText: {
        fontSize: 20,
        color: 'white',
        alignSelf: 'center'
    }
});