import {createWebHistory, createRouter} from 'vue-router'
/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限
 * meta : {
 noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
 title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
 icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
 breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
 activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
 }
 */

// 公共路由
export const constantRoutes = [
    {
        path: '/redirect',
        component: Layout,
        hidden: true,
        children: [
            {
                path: '/redirect/:path(.*)',
                component: () => import('@/views/redirect/index.vue')
            }
        ]
    },
    {
        path: '/login',
        component: () => import('@/views/login'),
        hidden: true
    },
    {
        path: '/register',
        component: () => import('@/views/register'),
        hidden: true
    },
    {
        path: "/:pathMatch(.*)*",
        component: () => import('@/views/error/404'),
        hidden: true
    },
    {
        path: '/401',
        component: () => import('@/views/error/401'),
        hidden: true
    },
    {
        path: '',
        component: Layout,
        redirect: '/index',
        children: [
            {
                path: '/index',
                component: () => import('@/views/index'),
                name: 'Index',
                meta: {title: '首页', icon: 'dashboard', affix: true}
            }
        ]
    },
    {
        path: '/user',
        component: Layout,
        hidden: true,
        redirect: 'noredirect',
        children: [
            {
                path: 'profile/:activeTab?',
                component: () => import('@/views/system/user/profile/index'),
                name: 'Profile',
                meta: {title: '个人中心', icon: 'user'}
            }
        ]
    },
    {
        name: "ResourcesRental",
        path: "/resourcesRental",
        hidden: false,
        redirect: "noRedirect",
        component: Layout,
        alwaysShow: true,
        meta: {
            title: "资源租赁",
            icon: "shopping",
            noCache: false,
            link: null,
        },
        children: [
            {
                name: "Book",
                path: "book",
                hidden: false,
                redirect: "noRedirect",
                alwaysShow: true,
                meta: {
                    title: "图书",
                    icon: "education",
                    noCache: false,
                    link: null,
                },
                children: [
                    {
                        name: "BookRental",
                        path: "bookRental",
                        hidden: false,
                        component: () => import('@/views/book/bookRental/index'),
                        meta: {
                            title: "图书租借",
                            icon: "user",
                            noCache: false,
                            link: null,
                        }
                    },
                    {
                        name: "BookRentalRecord",
                        path: "bookRentalRecord",
                        hidden: false,
                        component: () => import('@/views/book/rentalRecord/index'),
                        meta: {
                            title: "租借记录",
                            icon: "user",
                            noCache: false,
                            link: null,
                        }
                    },
                ]
            },
            {
                name: "Server",
                path: "server",
                hidden: false,
                redirect: "noRedirect",
                alwaysShow: true,
                meta: {
                    title: "服务器",
                    icon: "education",
                    noCache: false,
                    link: null,
                },
                children: [
                    {
                        name: "ServerRental",
                        path: "serverRental",
                        hidden: false,
                        component: () => import('@/views/server/serverRental/index'),
                        meta: {
                            title: "服务器租借",
                            icon: "user",
                            noCache: false,
                            link: null,
                        }
                    },
                    {
                        name: "ServerRentalRecord",
                        path: "serverRentalRecord",
                        hidden: false,
                        component: () => import('@/views/server/rentalRecord/index'),
                        meta: {
                            title: "租借记录",
                            icon: "user",
                            noCache: false,
                            link: null,
                        }
                    },
                ]
            },
        ]
    },
    {
        name: "System",
        path: "/system",
        hidden: false,
        redirect: "noRedirect",
        component: Layout,
        alwaysShow: true,
        meta: {
            title: "系统管理",
            icon: "system",
            noCache: false,
            link: null
        },
        children: [
            {
                name: "User",
                path: "user",
                hidden: false,
                component: () => import('@/views/system/user/index'),
                meta: {
                    title: "用户管理",
                    icon: "user",
                    noCache: false,
                    link: null
                }
            },
        ]
    },

]

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [
    {
        path: '/system/user-auth',
        component: Layout,
        hidden: true,
        permissions: ['system:user:edit'],
        children: [
            {
                path: 'role/:userId(\\d+)',
                component: () => import('@/views/system/user/authRole'),
                name: 'AuthRole',
                meta: {title: '分配角色', activeMenu: '/system/user'}
            }
        ]
    }

]

const router = createRouter({
    history: createWebHistory(),
    routes: constantRoutes,
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        }
        return {top: 0}
    },
})

export default router
