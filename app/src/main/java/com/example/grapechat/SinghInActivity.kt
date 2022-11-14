package com.example.grapechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.grapechat.databinding.ActivitySinghInBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SinghInActivity : AppCompatActivity() {
    lateinit var launcher : ActivityResultLauncher<Intent>
    lateinit var signInRequest : BeginSignInRequest
    lateinit var auth : FirebaseAuth
    lateinit var binding: ActivitySinghInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySinghInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try { //блок отслеживающий ошиибки
                val account = task.getResult(ApiException::class.java) //+
                if(account != null){
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException){   //--
                Log.d("MyLog", "Api error")
            }
        }
        binding.bSignIn.setOnClickListener {
            singInWithGoogle()
        }
    }


    private fun getClient(): GoogleSignInClient { //Запрос доступа к Гугл акаунту
        val gso = GoogleSignInOptions.Builder()
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.default_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build()

            )
            .build()
        return GoogleSignIn.getClient(this, gso.build())
    }

    private fun singInWithGoogle(){
        val singInClient = getClient()
        launcher.launch(singInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String){ //блок подключения акаунта к ФБ
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener() {
            if(it.isSuccessful){
                Log.d("MyLog","Google signIn done")
            } else{
                Log.d("MyLog", "Google signIn error")
            }
        }
    }
}

