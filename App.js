import React from 'react';
import Expo from 'expo';
import RNFetchBlob from 'react-native-fetch-blob';
import Spinner from 'react-native-loading-spinner-overlay';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View, WebView, ListView,
} from 'react-native';
var WEBVIEW_REF = 'webview';
import { SearchBar } from 'react-native-elements';
import Button from 'apsl-react-native-button'
import md5 from "react-native-md5";
import ListViewItem from './source/comp/Item';
export default class App extends React.Component {

  componentDidMount(){
     this.fetchData();
     this.setState({showWebview: 0});
     this.setState({ url: "http://google.com"});
  }

  constructor(props) {
    super(props);
    this.openProfile = this.openProfile.bind(this);
    this.state = {
      albums: [],
      showList: true,
      downloaded: false,
      query: "",
      response: "",
      char1: 'View song',
      char2:  'view video',
      showData: false,
      showWebview: 0,
      url: "http://google.com",
      dataSource: new ListView.DataSource({
        rowHasChanged: (r1, r2) => r1 !== r2
      }),
    }
  }

  createDataSource({ items }) {
   this.setState({
     dataSource: this.state.dataSource.cloneWithRows(items)
   });
  }

  openProfile(listItem){
    // this.props.navigation.navigate("Profile", { title: album.user.username });
    console.log("currentItem");
    console.log(listItem);
    this.setState({ showList: false })
    this.setState({ currentItem: listItem });
    this.setState({ showWebview: 400 });
    this.setState({ url: listItem.play_url})
 }
  fetchData(){
    var url = "http://bluewhaleapp.com/acts.php?"+"&id="+ Expo.Constants.deviceId;
    fetch(url)
    .then(response => response._bodyText)
    .then(responseData => {
    // console.log();
      // alert(Base64(responseData));
      var base64str  = Base64.atob(responseData);
      // var base64str  = Base64.atob(responseData);
      var json =  JSON.parse(base64str);
      // alert(json);
      try {
        this.setState( {char2: json[5]});
        this.setState( {char1: json[4]});
        this.setState({ showData: json[0]});
      }
      catch(err) {
      }
        // alert("da");
      // } else
    }).catch((error) => {
        console.error(error);
    });
  }

  _submitSearch(){
    var queryText = this.state.query;
    this.setState({ loading: true});
    console.log(Base64.btoa(queryText));
    let hex_md5v = md5.hex_md5(Expo.Constants.deviceId);
    var url = "http://bluewhaleapp.com/s3arc.php?srh="+ Base64.btoa(queryText) + "&id=" + hex_md5v;
    fetch(url)
    .then(response => response._bodyText)
    .then(responseData => {
    try {
      this.setState({ loading: false});
      var base64str  = Base64.atob(responseData);
      var json = JSON.parse(base64str);
      this.setState({ items: json });
      this.createDataSource(this.state);
    }
    catch(err) {
    }

    }).catch((error) => {
        console.error(error);
    });
  }

  renderRow(listItem) {
    return <ListViewItem
      method={() => this.openProfile(listItem)}
      key={listItem.play_code}
      item={listItem}
       />;
  }

  renderList(){
     if(this.state.showList == true){
       return   <View style={{ marginTop: 40, marginBottom: 30, backgroundColorss: 'blue'}}>
         <SearchBar
              lightTheme
              onChangeText={(text) => this.setState({ query: text})}
              onSubmitEditing={() => this._submitSearch()}
              placeholder='Search a song...'
               />
           <ListView
               enableEmptySections
               dataSource={this.state.dataSource}
               renderRow={this.renderRow.bind(this)}/>
         </View>;
     }
  }

  _onNavigationStateChange(webViewState){
  // (webViewState);
    console.log("webViewState");
    console.log(webViewState);
    var string = webViewState.url;
    if(string.indexOf("downloader") != -1 && this.state.downloaded == false){
      this.setState({ downloaded: true });
      RNFetchBlob.config({
            addAndroidDownloads : {
                useDownloadManager : true, // <-- this is the only thing required
                // Optional, override notification setting (default to true)
                notification : false,
                // Optional, but recommended since android DownloadManager will fail when
                mime : 'text/plain',
                description : 'File downloaded by download manager.'
            }
        })
        .fetch('GET', string)
        .then((resp) => {
          this.setState({ loading: false });
          // the path of downloaded file
          // alert(resp),
          this.setState({ showList: true}),
          this.setState({ showWebview: 0 })
          this.setState({ url: "http://google.com"})
          this.setState({ downloaded: false });
          resp.path()
        })
    }
 }

 renderFooter(){
  //  if(this.state.showList == true && this.state.showData == true){
  //    return  <AdMobBanner
  //        bannerSize="fullBanner"
  //        adUnitID="ca-app-pub-2640666806546520/8715577092"
  //        testDeviceID="EMULATOR"
  //        didFailToReceiveAdWithError={this.bannerError} />;
  //  }
 }
 renderLoading(){
   var str = "Loading. Please wait!";
   if(this.state.loading == true){
      return <Text style={{ fontSize: 22, alignItems: 'center', justifyContent: 'center', flexDirection: 'row' , marginLeft: 60, marginTop:100, marginBottom: 100}}>{str}</Text>
   }
 }

 renderButtons(){
   if(this.state.showList == false){
     if(this.state.showData == true){
       return <View style={{ flexDirection: 'column'}}>
       <Text>{this.state.currentItem.title}</Text>
       <Button  style={{ margin: 30, backgroundColor: 'blue' }} textStyle={{color: 'white'}} onPress={() => {
         this.setState({url : this.state.currentItem.dua});
         this.setState({showWebview: 0});
         this.setState({ loading: true});
         let jsCodeLoad = `
           document.getElementById('file').click();
        ;`;
      var counter = 0;
       var i = setInterval(function() {
         if(counter < 2) {
          counter ++;
          if(counter  == 1){
            this.webview.injectJavaScript(jsCodeLoad);
          }
        }

       }.bind(this), 2127);
     }} >{this.state.char1}</Button>
       <Button  style={{ margin: 30, backgroundColor: 'red' }} textStyle={{color: 'white'}} onPress={() => {
         this.setState({showWebview: 0}),
         this.setState({ loading: true});
         this.setState({url : this.state.currentItem.duv});
         let jsCodeLoad = `
                 document.getElementById('file').click();
              ;`;
         //  this.webview.injectJavaScript(jsCodeSend);
      var counter = 0;
       var i = setInterval(function() {
         if(counter < 2) {
          counter ++;
          if(counter  == 1){
            this.webview.injectJavaScript(jsCodeLoad);
          }
        }

      }.bind(this), 12127);

    }}>{this.state.char2}</Button>
    <Button isLoading={false} style={{ margin: 30 }} onPress={() => {
      this.setState({ showList: true}),
      this.setState({ showWebview: 0 })
      this.setState({ url: "http://google.com"})
      this.setState({ downloaded: false });
      this.setState({ loading: false });
    }}>Back</Button>
       </View>;
     } else {
       return <View style={{ flexDirection: 'column'}}>
            <Text>{this.state.currentItem.title}</Text>
            <Button style={{ margin: 30 }} onPress={() => {
              this.setState({ showList: true}),
              this.setState({ showWebview: 0 })
              this.setState({ url: "http://google.com"})
              this.setState({ downloaded: false });
              this.setState({ loading: false });
            }} >Back</Button></View>;
     }
  }
 }
  render() {
    return (
      <View  style={{ flex: 1, flexDirection: 'column'  }}>
       <Spinner cancelable={true} visible={this.state.loading} textContent={"Loading..."} textStyle={{color: '#FFF'}} />
      {this.renderFooter()}
      {this.renderList()}
      <View style={{ height: 30 }}/>
      <WebView
       ref={webview => { this.webview = webview; }}
       userAgent="Opera/9.80 (Android; Opera Mini/8.0.1807/36.1609; U; en) Presto/2.12.423 Version/12.16" //TODO change userAgent
       style={{ width: this.state.showWebview, height: this.state.showWebview }}
       mixedContentMode={'always'}
       javaScriptEnabled={true}
       domStorageEnabled={true}
       decelerationRate="normal"
       onNavigationStateChange={this._onNavigationStateChange.bind(this)}
       source={{uri: this.state.url }}/>
       {this.renderButtons()}
    </View>
    );
  }
}
const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
const Base64 = {
  btoa: (input:string = '')  => {
    let str = input;
    let output = '';

    for (let block = 0, charCode, i = 0, map = chars;
    str.charAt(i | 0) || (map = '=', i % 1);
    output += map.charAt(63 & block >> 8 - i % 1 * 8)) {

      charCode = str.charCodeAt(i += 3/4);

      if (charCode > 0xFF) {
        throw new Error("'btoa' failed: The string to be encoded contains characters outside of the Latin1 range.");
      }

      block = block << 8 | charCode;
    }

    return output;
  },

  atob: (input:string = '') => {
    let str = input.replace(/=+$/, '');
    let output = '';

    if (str.length % 4 == 1) {
      throw new Error("'atob' failed: The string to be decoded is not correctly encoded.");
    }
    for (let bc = 0, bs = 0, buffer, i = 0;
      buffer = str.charAt(i++);

      ~buffer && (bs = bc % 4 ? bs * 64 + buffer : buffer,
        bc++ % 4) ? output += String.fromCharCode(255 & bs >> (-2 * bc & 6)) : 0
    ) {
      buffer = chars.indexOf(buffer);
    }

    return output;
  }
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
