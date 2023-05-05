<template>
    <div id="app">
      <router-view/>
    </div>
  </template>
  
  <script>
  
  
  export default {
    name: 'App',
    components: {
      
    }
  }
  </script>
  
  <style>
  *{
      margin: 0;
      padding: 0
    }
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    overflow: hidden;
  }
  html,body{
    width: calc(100vw-30px);
     min-height: 100vh;
  }
  
  
  </style>
  
