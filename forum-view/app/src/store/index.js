import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
export default new Vuex.Store({
    state:{
        socket:null,
    },
    mutations:{
        setSocket(state,payload){
            state.socket=payload.socket;
        },
    },
    getters:{
        socket(state){
            return state.socket;
        }
    },
    actions:{

    },
    modules:{

    }
})