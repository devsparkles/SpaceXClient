package com.devsparkle.spacexclient.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
//
//    fun reset() {
//        this.launches = mutableListOf()
//        notifyDataSetChanged()
//    }

    fun updateLaunches(newlauches: List<Launch>) {
        this.launches = this.launches.plus(newlauches)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewHolderLaunchBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(clickCallback, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(launch = launches[position])
    }

    override fun getItemCount(): Int = launches.count()


    inner class DiffCallback(private val oldList: List<Launch>, private val newList: List<Launch>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].success === newList.get(newItemPosition).success
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition] == newList[newPosition]
        }

        @Nullable
        override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
            return super.getChangePayload(oldPosition, newPosition)
        }
    }

    inner class ViewHolder(
        private val clickCallback: (Launch) -> Unit,
        private val binding: ViewHolderLaunchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(launch: Launch) {

            Glide.with(binding.root.context).asBitmap().load(launch.missionPatchSmallImageUrl)
                .fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivPathSmall)

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
