package es.unex.giss.asee.whichnews.view.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import es.unex.giss.asee.whichnews.R
import es.unex.giss.asee.whichnews.data.models.User
import es.unex.giss.asee.whichnews.databinding.ActivityMainPageBinding

class MainPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainPageBinding


    private lateinit var newsFragment: NewsFragment
    private lateinit var trendingFragment: TrendingFragment
    private lateinit var favoritesFragment: FavoritesFragment

    companion object {
        const val USER_INFO = "USER_INFO"
        fun start(
            context: Context,
            user: User
        ){
            val intent = Intent(context, MainPageActivity::class.java).apply{
                putExtra(USER_INFO, user)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListeners()
    }

    private fun setupUI(){
        newsFragment = NewsFragment()
        trendingFragment = TrendingFragment()
        favoritesFragment = FavoritesFragment()

        // set News fragment as the default fragment in view.
        setCurrentFragment(newsFragment)
    }

    private fun setupListeners(){
        with(binding){
            bottomNavigation.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.action_news -> setCurrentFragment(newsFragment)
                    R.id.action_trending -> setCurrentFragment(trendingFragment)
                    R.id.action_favorites -> setCurrentFragment(favoritesFragment)
                }
                true
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
}