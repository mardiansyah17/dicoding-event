package com.example.dicodingevent.ui.detail_event

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.dicodingevent.R
import com.example.dicodingevent.data.Result
import com.example.dicodingevent.data.model.EventItem
import com.example.dicodingevent.databinding.ActivityDetailBinding
import com.example.dicodingevent.ui.EventViewModel
import com.example.dicodingevent.ui.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModel: EventViewModel by viewModels {
        factory
    }
    private lateinit var binding: ActivityDetailBinding

    private val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = args.idEvent
        viewModel.getDetailEvent(id)

        setContentView(binding.root)





        viewModel.getDetailEvent(id).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBarDetailEvent.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBarDetailEvent.visibility = View.GONE
                        val event = result.data

                        viewModel.getFavoriteEventById(id).observe(this) { isFavorite ->
                            if (isFavorite) {
                                binding.btnAddFavotite.setImageResource(R.drawable.baseline_favorite_24)
                            } else {
                                binding.btnAddFavotite.setImageResource(R.drawable.baseline_favorite_border_24)
                            }

                            binding.btnAddFavotite.setOnClickListener {
                                if (isFavorite) {
                                    viewModel.deleteFavoriteEventById(id)
                                } else {
                                    viewModel.addFavoriteEvent(
                                        EventItem(
                                            id = event.id,
                                            name = event.name,
                                            mediaCover = event.mediaCover
                                        )
                                    )
                                }
                            }
                        }

                        supportActionBar?.title = event.name

                        binding.tvOwnerDetailEvent.text =
                            getString(R.string.event_owner, event.ownerName)
                        binding.tvTitleDetailEvent.text = event.name
                        binding.tvQuotaDetailEvent.text = getString(
                            R.string.quota_remaining,
                            event.quote!!.minus(event.registrants!!)
                        )

                        binding.tvBeginTimeDetailEvent.text =
                            getString(R.string.begin_time, "10:00")

                        binding.tvDescDetailEvent.text =
                            HtmlCompat.fromHtml(
                                event.description!!,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            )

                        Glide.with(this)
                            .load(event.mediaCover).into(binding.ivCoverDetailEvent)

                        binding.buttonRegister.setOnClickListener {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(event.link)))
                        }
                    }

                    is Result.Error -> {
                        AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage(result.error)
                            .setPositiveButton("OK") { _, _ ->
                                finish()
                            }
                            .show()
                    }
                }
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
