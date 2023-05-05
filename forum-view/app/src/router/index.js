import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/login'
import Register from '../views/register'
import Main from '../views/main'
import Home from '../views/home'
import Bar from '../views/bar'
import Barcontent from '../views/barcontent'
import CommentL1 from '../views/commentL1'
import Person from '../views/person'
import Show from '../views/show.vue'
import ContentStore from '../views/contentstore.vue'
import BarContent1 from '../views/barcontent1.vue'
import BarContent2 from '../views/barcontent2.vue'
import InputContent from '../views/inputcontent.vue'
import Msg from '../views/msg.vue'
import BarList from '../views/barlist.vue'
import List from '../views/list.vue'
import ContentList from '../views/contentlist.vue'
import EditMsg from '../views/editmsg'
import Search from '../views/search.vue'
import BarResult from '../views/barresult.vue'
import ContentResult from '../views/contentresult.vue'
import Create from '../views/create.vue'
import EditContetnt from '../views/editcontent.vue'
import Ban from '../views/ban.vue'
Vue.use(VueRouter)

const subBarContent=[
    {path:'',redirect:"/main/bar/barcontent/barcontent1"},
    {path:'barcontent1',component:BarContent1},
    {path:'recommend',component:BarContent2},
    {path:'inputcontent',component:InputContent},
    {path:"ban",component:Ban}
]
const barChildren=[
    {path:'',redirect:"/main/bar/barcontent"},
    {path:'barcontent',component:Barcontent,children:subBarContent},
    {path:'commentL1',component:CommentL1},
]
const subperson=[
    {path:'',redirect:'/main/person/show'},
    {path:'show',component:Show},
    {path:'contentstore',component:ContentStore}
]
const subList=[
    {path:'',redirect:"/main/list/bar"},
    {path:'bar',component:BarList},
    {path:'content',component:ContentList}
]
const subSearch=[
    {path:'bar',component:BarResult},
    {path:'content',component:ContentResult}
]

const submain=[
    {path:'',redirect: '/main/home'},
    {path:'home',component:Home},
    {path:'bar',component:Bar,children:barChildren},
    {path:"person",component:Person,children:subperson},
    {path:'list',component:List,children:subList},
    {path:'editmsg',component:EditMsg},
    {path:'search',component:Search,children:subSearch},
    {path:'create',component:Create},
    {path:'editcontent',component:EditContetnt},
    {path:"msg",component:Msg}
];

const routes=[
    {path:"/login",component: Login},
    {path:'/register',component:Register},
    {path: '/main',component:Main,children:submain},
    {path:"/msg",component:Msg}
]





const router=new VueRouter({
    mode:'history',
    routes,
})

export default router