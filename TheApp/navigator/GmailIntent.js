/**
 * Created by Alex on 11/11/2016.
 */
import React, {Component} from 'react';
import {View, Text, TouchableHighlight, Linking, StyleSheet, UIExplorer, TouchableOpacity} from 'react-native';
export default class GmailIntent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <View style={styles.container}>
                <OpenURLButton url={'https://mail.google.com/mail/u/0/#inbox?compose=new'}/>
            </View>
        );
    }
}
export class OpenURLButton extends Component {
    static propTypes = {
        url: React.PropTypes.string,
    };

    handleClick = () => {
        Linking.canOpenURL(this.props.url).then(supported => {
            if (supported) {
                Linking.openURL(this.props.url);
            } else {
                console.log('Don\'t know how to open URI: ' + this.props.url);
            }
        });
    };

    render() {
        return (
            <View style={styles.container}>
                <TouchableOpacity style={styles.button}
                    onPress={this.handleClick}>
                    <View>
                        <Text style={styles.buttonText}>Open Gmail</Text>
                    </View>
                </TouchableOpacity>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        top: 10,
        padding: 5,
        flex:1
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