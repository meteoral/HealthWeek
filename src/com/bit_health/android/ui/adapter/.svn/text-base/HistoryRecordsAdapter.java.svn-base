package com.bit_health.android.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.beans.BpInfoBean;
import com.bit_health.android.controllers.beans.BsInfoBean;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.PpgInfoBean;
import com.bit_health.android.util.TimeFormatUtil;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类名：HistoryRecordsAdapter
 * 
 * 主要功能：ExpandableListView 的适配器，注：心电、脉搏、血压、血糖这几个模块的历史记录都共用这个适配器
 * 
 * @author 梁才学 创建日期：2013.12.9
 **********************************************************************/
public class HistoryRecordsAdapter extends BaseExpandableListAdapter {

	public static final int BS_MESURE_COUNT = 4;
	public static final int BP_MESURE_COUNT = 4;

	private String unitType;// 单位，如：bpm, mmHg, mmol/L
	private Context mContext;
	private LayoutInflater inflater;

	public List<Pair<String, List<JsonBase>>> mAdapterDatas = new ArrayList<Pair<String, List<JsonBase>>>();

	private String mesureType = "";

	public HistoryRecordsAdapter(Context context, String unit,
			List<JsonBase> datas) {
		mContext = context;
		inflater = LayoutInflater.from(context);
		unitType = unit;
		ChangeToAdapterData(datas);
	}

	public void ChangeToAdapterData(List<JsonBase> infos) {
		mAdapterDatas.clear();
		String timeStamp = null;
		String date = "";
		List<JsonBase> tempInfos = new ArrayList<JsonBase>();
		for (JsonBase bean : infos) {
			if (bean instanceof EcgInfoBean) {
				EcgInfoBean ecgInfo = (EcgInfoBean) bean;
				timeStamp = ecgInfo.mTimeStamp;
				mesureType = BusinessConst.ECG_MESURE;
			}
			if (bean instanceof PpgInfoBean) {
				PpgInfoBean ppgInfo = (PpgInfoBean) bean;
				timeStamp = ppgInfo.mTimeStamp;
				mesureType = BusinessConst.PPG_MESURE;
			}
			if (bean instanceof BsInfoBean) {
				BsInfoBean bsInfo = (BsInfoBean) bean;
				timeStamp = bsInfo.mTimeStamp;
				mesureType = BusinessConst.BS_MESURE;
			}
			if (bean instanceof BpInfoBean) {
				BpInfoBean bpInfo = (BpInfoBean) bean;
				timeStamp = bpInfo.mTimeStamp;
				mesureType = BusinessConst.BP_MESURE;
			}
			if (!TextUtils.isEmpty(timeStamp)) {

				// 服务器中，血糖、血压的日期格式形如：2014-01-22，而心电和脉搏的日期格式形如：20140122120125，所以需要分开处理
				if ((bean instanceof EcgInfoBean)
						|| (bean instanceof PpgInfoBean)) {
					if (TextUtils.isEmpty(date)) {
						date = timeStamp.substring(0, 8);
						tempInfos.add(bean);
					} else {
						if (date.equals(timeStamp.substring(0, 8))) {
							tempInfos.add(bean);
						} else {
							mAdapterDatas.add(new Pair<String, List<JsonBase>>(
									date, tempInfos));
							date = timeStamp.substring(0, 8);
							tempInfos = new ArrayList<JsonBase>();
							tempInfos.add(bean);
						}
					}
				} else {
					if (TextUtils.isEmpty(date)) {
						date = timeStamp;
						tempInfos.add(bean);
					} else {
						if (date.equals(timeStamp)) {
							tempInfos.add(bean);
						} else {
							mAdapterDatas.add(new Pair<String, List<JsonBase>>(
									date, tempInfos));
							date = timeStamp;
							tempInfos = new ArrayList<JsonBase>();
							tempInfos.add(bean);
						}
					}
				}

			}
		}
		if (tempInfos.size() > 0) {
			mAdapterDatas
					.add(new Pair<String, List<JsonBase>>(date, tempInfos));
		}
	}

	private int getActualPosition(int pos) {
		if (mesureType.equals(BusinessConst.BS_MESURE)) {
			return pos / BS_MESURE_COUNT;
		} else if (mesureType.equals(BusinessConst.BP_MESURE)) {
			return pos / BP_MESURE_COUNT;
		} else {
			return pos;
		}
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mAdapterDatas.get(groupPosition).second
				.get(getActualPosition(childPosition));
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ViewHolder mViewHolder = null;

		JsonBase childData = mAdapterDatas.get(groupPosition).second
				.get(getActualPosition(childPosition));

		/*****************************************************************
		 * 心电部分
		 ****************************************************************/
		if (childData instanceof EcgInfoBean) {
			EcgInfoBean ecgInfo = (EcgInfoBean) childData;

			int abnormalTextColor = mContext.getResources().getColor(
					R.color.color_red);
			int normalTextColor = mContext.getResources().getColor(
					R.color.black);
			int errorTextColor = mContext.getResources().getColor(
					R.color.lightwhite);
			int textColor = normalTextColor;

			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.xin_dian_history_records_child_item, null);
				mViewHolder = new ViewHolder();

				mViewHolder.time_text = (TextView) convertView
						.findViewById(R.id.xindian_time_id);
				mViewHolder.value_text = (TextView) convertView
						.findViewById(R.id.xindian_value_id);
				mViewHolder.state_text = (TextView) convertView
						.findViewById(R.id.xindian_state_id);
				mViewHolder.unit_text = (TextView) convertView
						.findViewById(R.id.xindian_unit_id);
				mViewHolder.child_item_bg = convertView
						.findViewById(R.id.xin_dian_history_child_item);

				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			mViewHolder.time_text.setText(TimeFormatUtil
					.turnTimeFormat(ecgInfo.mTimeStamp.substring(8, 12)));
			mViewHolder.value_text.setText("" + ecgInfo.mHeartRate);
			if (ecgInfo.mStatus == 0) {
				mViewHolder.state_text.setText("分析中");
			} else {
				if (ecgInfo.mFlagError == 0) {
					if (!ecgInfo.bIsNormal) {
						mViewHolder.state_text.setText("异常");
						textColor = abnormalTextColor;
					} else {
						mViewHolder.state_text.setText("正常");
					}
				} else {
					mViewHolder.state_text.setText("错误");
					textColor = errorTextColor;
				}
			}
			mViewHolder.unit_text.setText("bmp");
			mViewHolder.time_text.setTextColor(textColor);
			mViewHolder.value_text.setTextColor(textColor);
			mViewHolder.state_text.setTextColor(textColor);
			mViewHolder.unit_text.setTextColor(textColor);

			// 动态设置每一个child item 的背景图片
			if (mAdapterDatas.get(groupPosition).second.size() == 1) {
				mViewHolder.child_item_bg
						.setBackgroundResource(R.drawable.preference_single_item);
			} else if (mAdapterDatas.get(groupPosition).second.size() == 2) {
				if (childPosition == 0) {
					mViewHolder.child_item_bg
							.setBackgroundResource(R.drawable.preference_first_item);
				} else {
					mViewHolder.child_item_bg
							.setBackgroundResource(R.drawable.preference_last_item);
				}
			} else if (mAdapterDatas.get(groupPosition).second.size() > 2) {
				if (childPosition == 0) {
					mViewHolder.child_item_bg
							.setBackgroundResource(R.drawable.preference_first_item);
				} else if (childPosition == mAdapterDatas.get(groupPosition).second
						.size() - 1) {
					mViewHolder.child_item_bg
							.setBackgroundResource(R.drawable.preference_last_item);
				} else {
					mViewHolder.child_item_bg
							.setBackgroundResource(R.drawable.preference_item);
				}
			}

			return convertView;
		}
		
		/*****************************************************************
		 * 脉搏部分
		 ****************************************************************/
		if (childData instanceof PpgInfoBean) {
			PpgInfoBean ppgInfo = (PpgInfoBean) childData;

			int abnormalTextColor = mContext.getResources().getColor(
					R.color.color_red);
			int normalTextColor = mContext.getResources().getColor(
					R.color.black);
			int errorTextColor = mContext.getResources().getColor(
					R.color.lightwhite);
			int textColor = normalTextColor;

			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.xin_dian_history_records_child_item, null);
				mViewHolder = new ViewHolder();

				mViewHolder.time_text = (TextView) convertView
						.findViewById(R.id.xindian_time_id);
				mViewHolder.value_text = (TextView) convertView
						.findViewById(R.id.xindian_value_id);
				mViewHolder.state_text = (TextView) convertView
						.findViewById(R.id.xindian_state_id);
				mViewHolder.unit_text = (TextView) convertView
						.findViewById(R.id.xindian_unit_id);
				mViewHolder.child_item_bg = convertView
						.findViewById(R.id.xin_dian_history_child_item);

				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			mViewHolder.time_text.setText(TimeFormatUtil
					.turnTimeFormat(ppgInfo.mTimeStamp.substring(8, 12)));
			mViewHolder.value_text.setText("" + ppgInfo.mPulserate);
			if (ppgInfo.mStatus == 0) {
				mViewHolder.state_text.setText("分析中");
			} else {
				if (ppgInfo.mFlagError == 0) {
					if (!ppgInfo.bIsNormal) {
						mViewHolder.state_text.setText("异常");
						textColor = abnormalTextColor;
					} else {
						mViewHolder.state_text.setText("正常");
					}
				} else {
					mViewHolder.state_text.setText("错误");
					textColor = errorTextColor;
				}
			}
			mViewHolder.unit_text.setText("bmp");
			mViewHolder.time_text.setTextColor(textColor);
			mViewHolder.value_text.setTextColor(textColor);
			mViewHolder.state_text.setTextColor(textColor);
			mViewHolder.unit_text.setTextColor(textColor);

			// 动态设置每一个child item 的背景图片
			if (mAdapterDatas.get(groupPosition).second.size() == 1) {
				mViewHolder.child_item_bg
						.setBackgroundResource(R.drawable.preference_single_item);
			} else if (mAdapterDatas.get(groupPosition).second.size() == 2) {
				if (childPosition == 0) {
					mViewHolder.child_item_bg
							.setBackgroundResource(R.drawable.preference_first_item);
				} else {
					mViewHolder.child_item_bg
							.setBackgroundResource(R.drawable.preference_last_item);
				}
			} else if (mAdapterDatas.get(groupPosition).second.size() > 2) {
				if (childPosition == 0) {
					mViewHolder.child_item_bg
							.setBackgroundResource(R.drawable.preference_first_item);
				} else if (childPosition == mAdapterDatas.get(groupPosition).second
						.size() - 1) {
					mViewHolder.child_item_bg
							.setBackgroundResource(R.drawable.preference_last_item);
				} else {
					mViewHolder.child_item_bg
							.setBackgroundResource(R.drawable.preference_item);
				}
			}
			return convertView;
		}

		/*****************************************************************
		 * 血糖部分
		 ****************************************************************/
		if (childData instanceof BsInfoBean) {

			int abnormalTextColor = mContext.getResources().getColor(
					R.color.color_red);
			int normalTextColor = mContext.getResources().getColor(
					R.color.black);
			int textColor = normalTextColor;

			BsInfoBean bsInfo = (BsInfoBean) childData;
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.xue_tang_history_records_child_item, null);
				mViewHolder = new ViewHolder();

				mViewHolder.futangOrKongfu = (TextView) convertView
						.findViewById(R.id.futang_or_kongfu_id);
				mViewHolder.value_text = (TextView) convertView
						.findViewById(R.id.value_id);
				mViewHolder.state_text = (TextView) convertView
						.findViewById(R.id.state_id);
				mViewHolder.unit_text = (TextView) convertView
						.findViewById(R.id.unit_id);
				mViewHolder.child_item_bg = convertView
						.findViewById(R.id.xue_tang_history_child_item);
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}

			String valueState = "";
			String bsValue = "";
			int iChildPos = childPosition % BS_MESURE_COUNT;
			if (iChildPos == 0) {
				valueState = "空腹";
				bsValue = bsInfo.mLimosis;
				mViewHolder.state_text.setText(bsInfo.mLimosis_value_ab);
			} else if (iChildPos == 1) {
				valueState = "午餐前";
				bsValue = bsInfo.mBLunch;
				mViewHolder.state_text.setText(bsInfo.mBefore_lunch_value_ab);
			} else if (iChildPos == 2) {
				valueState = "午餐后2小时";
				bsValue = bsInfo.mALunch;
				mViewHolder.state_text.setText(bsInfo.mAfter_lunch_value_ab);
			} else if (iChildPos == 3) {
				valueState = "睡前";
				bsValue = bsInfo.mBSleep;
				mViewHolder.state_text.setText(bsInfo.mBefore_sleep_value_ab);
			}

			mViewHolder.futangOrKongfu.setText(valueState);
			mViewHolder.value_text.setText(bsValue);
			mViewHolder.unit_text.setText("mmol/L");

			if (!mViewHolder.state_text.getText().toString().equals("正常")) {
				textColor = abnormalTextColor;
			}
			mViewHolder.futangOrKongfu.setTextColor(textColor);
			mViewHolder.value_text.setTextColor(textColor);
			mViewHolder.state_text.setTextColor(textColor);
			mViewHolder.unit_text.setTextColor(textColor);

			// 动态设置每一个child item 的背景图片
			if (childPosition == 0) {
				mViewHolder.child_item_bg
						.setBackgroundResource(R.drawable.preference_first_item);
			} else if (childPosition == mAdapterDatas.get(groupPosition).second
					.size() * BS_MESURE_COUNT - 1) {
				mViewHolder.child_item_bg
						.setBackgroundResource(R.drawable.preference_last_item);
			} else {
				mViewHolder.child_item_bg
						.setBackgroundResource(R.drawable.preference_item);
			}
		}

		/*****************************************************************
		 * 血压部分
		 ****************************************************************/
		if (childData instanceof BpInfoBean) {

			int abnormalTextColor = mContext.getResources().getColor(
					R.color.color_red);
			int normalTextColor = mContext.getResources().getColor(
					R.color.black);
			int textColor = normalTextColor;

			BpInfoBean bpInfo = (BpInfoBean) childData;
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.xin_dian_history_records_child_item, null);
				mViewHolder = new ViewHolder();

				mViewHolder.time_text = (TextView) convertView
						.findViewById(R.id.xindian_time_id);
				mViewHolder.value_text = (TextView) convertView
						.findViewById(R.id.xindian_value_id);
				mViewHolder.state_text = (TextView) convertView
						.findViewById(R.id.xindian_state_id);
				mViewHolder.unit_text = (TextView) convertView
						.findViewById(R.id.xindian_unit_id);
				mViewHolder.child_item_bg = convertView
						.findViewById(R.id.xin_dian_history_child_item);

				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}

			String valueState = "";
			int bpValue = 0;
			int iChildPos = childPosition % BP_MESURE_COUNT;
			if (iChildPos == 0) {
				valueState = "睡觉前收缩压";
				bpValue = bpInfo.mSleepSpb;
			} else if (iChildPos == 1) {
				valueState = "睡觉前舒张压";
				bpValue = bpInfo.mSleepDpb;
			}
			// else if (iChildPos == 2) {
			// valueState = "睡觉前脉率";
			// bpValue = bpInfo.mSleepPR;
			// }
			else if (iChildPos == 2) {
				valueState = "起床后收缩压";
				bpValue = bpInfo.mWakeSpb;
			} else if (iChildPos == 3) {
				valueState = "起床后舒张压";
				bpValue = bpInfo.mWakeDpb;
			}
			// else if (iChildPos == 5) {
			// valueState = "起床后脉率";
			// bpValue = bpInfo.mWakePR;
			// }

			mViewHolder.time_text.setText(valueState);
			mViewHolder.value_text.setText(bpValue + "");
			if (bpInfo.mAbnormal.equals("正常")) {
				mViewHolder.state_text.setText("正常");
			} else {
				mViewHolder.state_text.setText("异常");
				textColor = abnormalTextColor;
			}
			mViewHolder.unit_text.setText("mmHg");
			mViewHolder.time_text.setTextColor(textColor);
			mViewHolder.value_text.setTextColor(textColor);
			mViewHolder.state_text.setTextColor(textColor);
			mViewHolder.unit_text.setTextColor(textColor);

			// 动态设置每一个child item 的背景图片
			if (childPosition == 0) {
				mViewHolder.child_item_bg
						.setBackgroundResource(R.drawable.preference_first_item);
			} else if (childPosition == mAdapterDatas.get(groupPosition).second
					.size() * BP_MESURE_COUNT - 1) {
				mViewHolder.child_item_bg
						.setBackgroundResource(R.drawable.preference_last_item);
			} else {
				mViewHolder.child_item_bg
						.setBackgroundResource(R.drawable.preference_item);
			}
			return convertView;
		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (groupPosition >= mAdapterDatas.size()) {
			return 0;
		}
		if (mesureType.equals(BusinessConst.BS_MESURE)) {
			return mAdapterDatas.get(groupPosition).second.size()
					* BS_MESURE_COUNT;
		} else if (mesureType.equals(BusinessConst.BP_MESURE)) {
			return mAdapterDatas.get(groupPosition).second.size()
					* BP_MESURE_COUNT;
		} else {
			return mAdapterDatas.get(groupPosition).second.size();
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		String dateValue = mAdapterDatas.get(groupPosition).first;
		return TimeFormatUtil.turnDateFormat(dateValue);
	}

	@Override
	public int getGroupCount() {
		return mAdapterDatas.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewHolder mViewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.xin_dian_history_records_group_item, null);
			mViewHolder = new ViewHolder();

			mViewHolder.groupNameTextView = (TextView) convertView
					.findViewById(R.id.buddy_listview_group_name);
			mViewHolder.image = (ImageView) convertView
					.findViewById(R.id.buddy_listview_image);

			convertView.setTag(mViewHolder);

		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		// if (mesureType.equals(BusinessConst.BS_MESURE)) {
		// mViewHolder.groupNameTextView.setText(mAdapterDatas
		// .get(groupPosition).first);
		// } else {
		// mViewHolder.groupNameTextView.setText(getGroup(groupPosition)
		// .toString());
		// }

		if (mesureType.equals(BusinessConst.ECG_MESURE)
				|| mesureType.equals(BusinessConst.PPG_MESURE)) {
			mViewHolder.groupNameTextView.setText(setDateTimeForm(getGroup(
					groupPosition).toString()));
		} else {
			mViewHolder.groupNameTextView.setText(setDateTimeForm(mAdapterDatas
					.get(groupPosition).first));
		}

		mViewHolder.image.setImageResource(R.drawable.xindian_down);

		// 更换展开分组图片
		if (!isExpanded) {
			mViewHolder.image
					.setImageResource(R.drawable.abnormal_pointer_normal);
		}
		return convertView;
	}

	/*****************************************************************
	 * 方法描述：转换日期显示，将今天和昨天的日期以“今天”、“昨天”来显示，其他时间显示具体的日期，如 03-18
	 * 
	 * @param dateTime
	 *            2014-03-18
	 ****************************************************************/
	private String setDateTimeForm(String dateTime) {
		return TimeFormatUtil.bsOrBpShowTime(dateTime);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	// 子选项是否可以选择
	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	class ViewHolder {
		// child 部分
		TextView time_text;// 时间
		TextView futangOrKongfu;// 服糖或空腹
		TextView value_text;// 值
		TextView unit_text;// 单位
		TextView state_text;// 状态
		View child_item_bg;// 每个子item的背景

		// group 部分
		TextView groupNameTextView;// 日期
		ImageView image;// 箭头
	}
}