/**
 * Created by Alex on 11/8/2016.
 */
import React, {Component} from 'react';
import {View, Text, TouchableHighlight, StyleSheet, ListView, AsyncStorage, TextInput, Image,ScrollView,AppRegistry} from 'react-native';
export default class PotatoList extends Component {

    async fetchData() {
        const potatoes = await AsyncStorage.getItem("potatoes");
        const response = await JSON.parse(potatoes);
        const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this.setState({ 
        dataSource : ds.cloneWithRows(response)
        });
    }

    constructor(props) {
        super(props);
        const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this.state = { 
            dataSource : ds.cloneWithRows([{}]),
            potato : {name: '', id: ''}
        };
}

    componentWillMount(){
        this.fetchData().done();
    }

    navSecond() {
        this.props.navigator.push({
            id: 'second'
        })
    }

    async addPotato(){
        if(this.state.potato.name === '' || this.state.potato.id === ''){
            return;
        }
        let potatoes = await AsyncStorage.getItem("potatoes");
        let updated = JSON.parse(potatoes);
        updated.push(this.state.potato);
        AsyncStorage.setItem("potatoes", JSON.stringify(updated), ()=>{ this.fetchData();})
        this.state.potato.name='';
        this.state.potato.id = '';
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
                    onChangeText={(text) => {
                        this.state.potato.name = text
                    }}/>
                <TextInput
                placeholder='New potato id'
                 onChangeText={(text) => {
                        this.state.potato.id = text
                }}/>
                <TouchableHighlight onPress={this.addPotato.bind(this)}>
                    <Text>Add potato</Text>
                </TouchableHighlight>
                
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
            potato: {id: this.props.id, name: this.props.name}
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