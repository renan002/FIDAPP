package br.com.micdev.fid2.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import br.com.micdev.fid2.login.LoginResponse
import br.com.micdev.fid2.util.PreferencesUtility.LOGGED_IN_PREF
import br.com.micdev.fid2.util.PreferencesUtility.USER_EVENTS_NAO_PAGOS
import br.com.micdev.fid2.util.PreferencesUtility.USER_EVENTS_NAO_PAGOS_DATE
import br.com.micdev.fid2.util.PreferencesUtility.USER_EVENTS_PAGO
import br.com.micdev.fid2.util.PreferencesUtility.USER_EVENTS_PAGOS_DATE
import br.com.micdev.fid2.util.PreferencesUtility.USER_EVENTS_PROPRIOS
import br.com.micdev.fid2.util.PreferencesUtility.USER_EVENTS_PROPRIOS_DATE
import br.com.micdev.fid2.util.PreferencesUtility.USER_ID
import br.com.micdev.fid2.util.PreferencesUtility.USER_LOGIN
import br.com.micdev.fid2.util.PreferencesUtility.USER_NAME
import br.com.micdev.fid2.util.PreferencesUtility.USER_TOKEN
import java.util.*


object SaveSharedPreference {

    internal fun getPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    fun setLoggedIn(context: Context, loggedIn: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(LOGGED_IN_PREF, loggedIn)
        editor.apply()
    }

    /**
     * @param context
     * @param loginResponse
     */
    fun setLoginParams(context: Context,loginResponse: LoginResponse?){
        setLoggedIn(context,true)
        setUserId(context,loginResponse!!.userId)
        setUserLogin(context,loginResponse.login)
        setUserName(context,loginResponse.name)
        setUserToken(context,loginResponse.token)
    }

    /**
     * @param context
     * @param jsonEventosPagos
     */
    fun setEventosPagos(context: Context,jsonEventosPagos:String){
        val editor = getPreferences(context).edit()
        editor.putString(USER_EVENTS_PAGO,jsonEventosPagos)
        editor.putLong(USER_EVENTS_PAGOS_DATE, System.currentTimeMillis())
        editor.apply()
    }

    /**
     * @param  context
     * @return string: eventos pagos em json
     */
    fun getEventosPagos(context: Context):String?{
        return getPreferences(context).getString(USER_EVENTS_PAGO,null)
    }

    /**
     * @param context
     * @return long: data da ultima chamada da lista pago
     */
    fun getEventosPagosDate(context: Context):Long{
        return getPreferences(context).getLong(USER_EVENTS_PAGOS_DATE,0L)
    }

    /**
     * @param context
     * @param jsonEventosProprios
     */
    fun setEventosProprios(context: Context,jsonEventosProprios: String){
        val editor = getPreferences(context).edit()
        editor.putString(USER_EVENTS_PROPRIOS,jsonEventosProprios)
        editor.putLong(USER_EVENTS_PROPRIOS_DATE, System.currentTimeMillis())
        editor.apply()
    }

    /**
     * @param context
     * @return string: eventos proprios em json
     */
    fun getEventosProprios(context: Context):String?{
        return getPreferences(context).getString(USER_EVENTS_PROPRIOS,null)
    }

    /**
     * @param context
     * @return long: data da ultima chamada da lista de proprio
     */
    fun getEventosPropriosDate(context: Context):Long{
        return getPreferences(context).getLong(USER_EVENTS_PROPRIOS_DATE,0L)
    }

    /**
     * @param context
     * @param jsonEventosNaoPagos
     */
    fun setEventosNaoPagos(context: Context,jsonEventosNaoPagos:String){
        val editor = getPreferences(context).edit()
        editor.putString(USER_EVENTS_NAO_PAGOS,jsonEventosNaoPagos)
        editor.putLong(USER_EVENTS_NAO_PAGOS_DATE, System.currentTimeMillis())
        editor.apply()
    }

    /**
     * @param context
     * @return string: eventos nao pagos em json
     */
    fun getEventosNaoPagos(context: Context):String?{
        return getPreferences(context).getString(USER_EVENTS_NAO_PAGOS,null)
    }

    /**
     * @param context
     * @return long: data da ultima chamada da lista de nao pagos
     */

    fun getEventosNaoPagosDate(context: Context):Long{
        return getPreferences(context).getLong(USER_EVENTS_NAO_PAGOS_DATE,0L)
    }

    /**
     * @param context
     */
    fun logout(context: Context){
        val editor = getPreferences(context).edit()
        editor.putBoolean(LOGGED_IN_PREF, false)
        editor.clear()
        editor.apply()
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    fun getLoggedStatus(context: Context): Boolean {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false)
    }

    /**
     * @param context
     * @return string: user token
     */
    fun getUserToken(context: Context): String? {
        return getPreferences(context).getString(USER_TOKEN,null)
    }

    /**
     * @param context
     * @param userToken
     */
    fun setUserToken(context: Context, userToken: String) {
        val editor = getPreferences(context).edit()
        editor.putString(USER_TOKEN,userToken)
        editor.apply()
    }

    /**
     * @param context
     * @return string: user name
     */
    fun getUserName(context: Context): String?{
        return getPreferences(context).getString(USER_NAME,null)
    }

    /**
     * @param context
     * @param userName
     */
    fun setUserName(context: Context,userName:String){
        val editor = getPreferences(context).edit()
        editor.putString(USER_NAME,userName)
        editor.apply()
    }

    /**
     * @param context
     * @return string: user id
     */
    fun getUserId(context: Context):String?{
        return getPreferences(context).getString(USER_ID,null)
    }

    /**
     * @param context
     * @param userId
     */
    fun setUserId(context: Context, userId:String){
        val editor = getPreferences(context).edit()
        editor.putString(USER_ID,userId)
        editor.apply()
    }

    /**
     * @param context
     * @return string: user login
     */
    fun getUserLogin(context: Context):String?{
        return getPreferences(context).getString(USER_LOGIN,null)
    }

    /**
     * @param context
     * @param userLogin
     */
    fun setUserLogin(context: Context, userLogin:String){
        val editor = getPreferences(context).edit()
        editor.putString(USER_LOGIN,userLogin)
        editor.apply()
    }
}