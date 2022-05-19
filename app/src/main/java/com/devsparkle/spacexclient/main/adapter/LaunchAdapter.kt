package com.devsparkle.spacexclient.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devsparkle.spacexclient.R
import com.devsparkle.spacexclient.databinding.ViewHolderLaunchBinding
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.utils.DateUtilsConstants
import com.devsparkle.spacexclient.utils.IsFutureDate
import com.devsparkle.spacexclient.utils.daysSinceOrFromDate
import com.devsparkle.spacexclient.utils.launchDate
import com.devsparkle.spacexclient.utils.launchTime
import com.devsparkle.spacexclient.utils.toDate

class LaunchAdapter(private val clickCallback: ((Launch) -> Unit)) :
    RecyclerView.Adapter<LaunchAdapter.ViewHolder>() {
    private var launches: List<Launch> = emptyList()

    fun reset() {
        this.launches = emptyList()
        notifyDataSetChanged()
    }

    fun updateLaunches(lauches: List<Launch>) {
        this.launches = this.launches.plus(lauches)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewHolderLaunchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(clickCallback, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(launch = launches[position])
    }

    override fun getItemCount(): Int = launches.count()

    class ViewHolder(
        private val clickCallback: (Launch) -> Unit,
        private val binding: ViewHolderLaunchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(launch: Launch) {

            Glide.with(binding.root.context).asBitmap().load(launch.missionPatchSmallImageUrl)
                .fitCenter().into(binding.ivPathSmall)

            binding.tvMissionValue.text = launch.missionName


            launch.dateUtc?.toDate(DateUtilsConstants.DATE_UTC_FORMAT)?.let {
                binding.tvDateTimeValue.text =
                    binding.root.context.getString(
                        R.string.date_time_value,
                        it.launchDate(),
                        it.launchTime()
                    )

                binding.tvDaysSinceFromNow.text = binding.root.context.getString(
                    R.string.label_days_since_from_now,
                    if (it.IsFutureDate()) "from" else "since"
                )
                binding.tvDaysSinceFromNowValue.text = it.daysSinceOrFromDate().toString()

            }

            binding.tvRocketValue.text = binding.root.context.getString(
                R.string.rocket_value,
                launch.rocket?.name,
                launch.rocket?.type
            )


            launch.success?.let {
                if (it) {
                    Glide.with(binding.root.context).asBitmap()
                        .load(R.drawable.ic_baseline_done_24)
                        .fitCenter().into(binding.ivSuccessFail)
                } else {
                    Glide.with(binding.root.context).asBitmap()
                        .load(R.drawable.ic_baseline_clear_24)
                        .fitCenter().into(binding.ivSuccessFail)
                }
            }


            binding.root.setOnClickListener { clickCallback(launch) }

        }
    }
}
