/**
 * Created by Alex on 11/8/2016.
 */
import React, {Component} from 'react';
import {View, Text, TouchableHighlight, StyleSheet, ListView} from 'react-native';
export default class First extends Component {
    constructor(props) {
        super(props);
        const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this.state = {
            dataSource: ds.cloneWithRows([{id:1, name: 'potato1'}, {id:2, name: 'potato2'}, {id:3 ,name: 'potato3'}, {id:4, name: 'potato4'}]),
        };
    }

    navSecond() {
        this.props.navigator.push({
            id: 'second'
        })
    }

    render() {
        return (
            <View style={styles.container}>
                <TouchableHighlight onPress={this.navSecond.bind(this)}>
                    <Text>Go to logIn</Text>
                </TouchableHighlight>

                <ListView dataSource={this.state.dataSource}
                          renderRow={(data) => <Row navigator={this.props.navigator}  {...data} />}/>
            </View>
        );
    }
}

export class Row extends Component {
    constructor(props) {
        super(props);
    }

    navEdit() {
        console.log(this.props.name);
        this.props.navigator.push({
            id: 'edit',
            potato: {id: this.props.id, name: this.props.name}
        })
    }


    render() {
        return (
            <View>
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
        padding: 5,
    }
});