package dimitris.pallas.stoiximan.stoiximanapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import dimitris.pallas.stoiximan.stoiximanapp.R
import dimitris.pallas.stoiximan.stoiximanapp.presentation.fragment.SportFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.container, SportFragment.newInstance())
                .commit()
        }
    }
}