package com.devsparkle.spacexclient.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devsparkle.spacexclient.R
import com.devsparkle.spacexclient.databinding.ViewHolderLaunchBinding
import com.devsparkle.spacexclient.domain.model.Launch

class LaunchAdapter(private val clickCallback: ((Launch) -> Unit)) :
    RecyclerView.Adapter<LaunchAdapter.ViewHolder>() {
    private var launches: List<Launch> = emptyList()

    fun reset() {
        this.launches = emptyList()
        notifyDataSetChanged()
    }

    fun updateLaunches(issues: List<Launch>) {
        this.launches = this.launches.plus(issues)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchAdapter.ViewHolder {
        val binding =
            ViewHolderLaunchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(clickCallback,binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(launch = launches[position])
    }

    override fun getItemCount(): Int = launches.count()

    class ViewHolder(private val clickCallback: (Launch) -> Unit,
        private val binding: ViewHolderLaunchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(launch: Launch) {

            Glide.with(binding.root.context).asBitmap().load(launch.missionPatchSmallImageUrl)
                .fitCenter().into(binding.ivPathSmall)

            binding.tvMissionValue.text = launch.missionName
            binding.tvDateTimeValue.text =
                binding.root.context.getString(R.string.date_time_value, launch.date, launch.time)
            binding.tvRocketValue.text = binding.root.context.getString(
                R.string.rocket_value,
                launch.rocket.name,
                launch.rocket.type
            )

            // today - launchdate = days since/from now
            // if today-launchdate = negative it's in the future(from) if it's positive it's in the past(since)
            // convert to days
//            binding.tvDaysSinceFromNow.text =
//            binding.tvDaysSinceFromNowValue.text =
            if (launch.success) {
                Glide.with(binding.root.context).asBitmap().load(R.drawable.ic_baseline_done_24)
                    .fitCenter().into(binding.ivSuccessFail)
            } else {
                Glide.with(binding.root.context).asBitmap().load(R.drawable.ic_baseline_clear_24)
                    .fitCenter().into(binding.ivSuccessFail)
            }

            binding.root.setOnClickListener { clickCallback(launch) }

        }
    }
}