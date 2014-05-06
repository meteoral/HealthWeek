package com.bit_health.android.configuration;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.beans.BpScoreBean;
import com.bit_health.android.controllers.beans.BsScoreBean;
import com.bit_health.android.controllers.beans.EcgScoreBean;
import com.bit_health.android.controllers.beans.OverScoreBean;
import com.bit_health.android.controllers.beans.PpgScoreBean;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;


public class AnalyzeCath {
	static private AnalyzeCath instance;
	public static final String ANALYZE_CONFIG = "analyzeconfig";
	private Context mContext;
	private SharedPreferences mSharedPreferences;
	private Editor mEditor;

	public List<EcgScoreBean> mListEcgScores = new ArrayList<EcgScoreBean>();
	public List<PpgScoreBean> mListPpgScores = new ArrayList<PpgScoreBean>();
	public List<BpScoreBean> mListBpScores = new ArrayList<BpScoreBean>();
	public List<BsScoreBean> mListBsScores = new ArrayList<BsScoreBean>();
	public List<OverScoreBean> mListOverScores = new ArrayList<OverScoreBean>();

	private AnalyzeCath(Context contex) {
		mContext = contex.getApplicationContext();
		mSharedPreferences = mContext.getSharedPreferences(ANALYZE_CONFIG, 0);
		load();
		mEditor = mSharedPreferences.edit();
	}

	/**********************************************************
	 * 方法描述：
	 * 
	 * @param context
	 *            这个context不能是Activity的，必须是全局的, 即 Application的
	 * @return
	 **********************************************************/
	static public AnalyzeCath getInstance(Context context) {
		if (instance == null) {
			instance = new AnalyzeCath(context);
		}
		return instance;
	}

	private void load() {
		RoleCatchInfo rolecath = RoleCatchInfo.getInstance(mContext);
		List<RoleInfoBean> roles = rolecath.getRoles();
		for (RoleInfoBean role : roles) {
			EcgScoreBean ecgScore = loadEcg(role.mId);
			if (ecgScore != null) {
				mListEcgScores.add(ecgScore);
			}
			PpgScoreBean ppgScore = loadPpg(role.mId);
			if (ppgScore != null) {
				mListPpgScores.add(ppgScore);
			}
			BpScoreBean bpScore = loadBp(role.mId);
			if (bpScore != null) {
				mListBpScores.add(bpScore);
			}
			BsScoreBean bsScore = loadBs(role.mId);
			if (bsScore != null) {
				mListBsScores.add(bsScore);
			}
			OverScoreBean overScore = loadOverall(role.mId);
			if (overScore != null) {
				mListOverScores.add(overScore);
			}
		}
	}

	public void saveEcg(EcgScoreBean bean) {
		if(TextUtils.isEmpty(bean.mUserId)){
			return;
		}
		boolean bfind = false;
		for (EcgScoreBean ecg : mListEcgScores) {
			if (ecg.mUserId.equals(bean.mUserId)) {
				bfind = true;
				mListEcgScores.remove(ecg);
				mListEcgScores.add(bean);
				try {
					mEditor.putString(bean.mUserId + "_"
							+ BusinessConst.SCORE_TYPE_ECG, bean.toJson());
					mEditor.commit();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		if (!bfind) {
			mListEcgScores.add(bean);
			try {
				mEditor.putString(bean.mUserId + "_"
						+ BusinessConst.SCORE_TYPE_ECG, bean.toJson());
				mEditor.commit();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private EcgScoreBean loadEcg(String userid) {
		String value = mSharedPreferences.getString(userid + "_"
				+ BusinessConst.SCORE_TYPE_ECG, null);
		if (value != null) {
			EcgScoreBean bean = new EcgScoreBean();
			try {
				bean.parser(value);
				return bean;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private PpgScoreBean loadPpg(String userid) {
		String value = mSharedPreferences.getString(userid + "_"
				+ BusinessConst.SCORE_TYPE_PPG, null);
		if (value != null) {
			PpgScoreBean bean = new PpgScoreBean();
			try {
				bean.parser(value);
				return bean;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public void savePpg(PpgScoreBean bean) {
		if(TextUtils.isEmpty(bean.mUserId)){
			return;
		}
		boolean bfind = false;
		for (PpgScoreBean ppg : mListPpgScores) {
			if (ppg.mUserId.equals(bean.mUserId)) {
				bfind = true;
				mListPpgScores.remove(ppg);
				mListPpgScores.add(bean);
				try {
					mEditor.putString(bean.mUserId + "_"
							+ BusinessConst.SCORE_TYPE_PPG, bean.toJson());
					mEditor.commit();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		if (!bfind) {
			mListPpgScores.add(bean);
			try {
				mEditor.putString(bean.mUserId + "_"
						+ BusinessConst.SCORE_TYPE_PPG, bean.toJson());
				mEditor.commit();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private BpScoreBean loadBp(String userid) {
		String value = mSharedPreferences.getString(userid + "_"
				+ BusinessConst.SCORE_TYPE_BP, null);
		if (value != null) {
			BpScoreBean bean = new BpScoreBean();
			try {
				bean.parser(value);
				return bean;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public void saveBp(BpScoreBean bean) {
		if(TextUtils.isEmpty(bean.mUserId)){
			return;
		}
		boolean bfind = false;
		for (BpScoreBean bp : mListBpScores) {
			if (bp.mUserId.equals(bean.mUserId)) {
				bfind = true;
				mListBpScores.remove(bp);
				mListBpScores.add(bean);
				try {
					mEditor.putString(bean.mUserId + "_"
							+ BusinessConst.SCORE_TYPE_BP, bean.toJson());
					mEditor.commit();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		if (!bfind) {
			mListBpScores.add(bean);
			try {
				mEditor.putString(bean.mUserId + "_"
						+ BusinessConst.SCORE_TYPE_BP, bean.toJson());
				mEditor.commit();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private BsScoreBean loadBs(String userid) {
		String value = mSharedPreferences.getString(userid + "_"
				+ BusinessConst.SCORE_TYPE_BS, null);
		if (value != null) {
			BsScoreBean bean = new BsScoreBean();
			try {
				bean.parser(value);
				return bean;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public void saveBs(BsScoreBean bean) {
		if(TextUtils.isEmpty(bean.mUserId)){
			return;
		}
		boolean bfind = false;
		for (BsScoreBean bs : mListBsScores) {
			if (bs.mUserId.equals(bean.mUserId)) {
				bfind = true;
				mListBsScores.remove(bs);
				mListBsScores.add(bean);
				try {
					mEditor.putString(bean.mUserId + "_"
							+ BusinessConst.SCORE_TYPE_BS, bean.toJson());
					mEditor.commit();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		if (!bfind) {
			mListBsScores.add(bean);
			try {
				mEditor.putString(bean.mUserId + "_"
						+ BusinessConst.SCORE_TYPE_BS, bean.toJson());
				mEditor.commit();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private OverScoreBean loadOverall(String userid) {
		String value = mSharedPreferences.getString(userid + "_"
				+ BusinessConst.SCORE_TYPE_OVERALL, null);
		if (value != null) {
			OverScoreBean bean = new OverScoreBean();
			try {
				bean.parser(value);
				return bean;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public void saveOverall(OverScoreBean bean) {
		if(TextUtils.isEmpty(bean.mUserId)){
			return;
		}
		boolean bfind = false;
		for (OverScoreBean overall : mListOverScores) {
			if (overall.mUserId.equals(bean.mUserId)) {
				bfind = true;
				mListOverScores.remove(overall);
				mListOverScores.add(bean);
				try {
					mEditor.putString(bean.mUserId + "_"
							+ BusinessConst.SCORE_TYPE_OVERALL, bean.toJson());
					mEditor.commit();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		if (!bfind) {
			mListOverScores.add(bean);
			try {
				mEditor.putString(bean.mUserId + "_"
						+ BusinessConst.SCORE_TYPE_OVERALL, bean.toJson());
				mEditor.commit();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void clear() {
		mEditor.clear();
		mEditor.commit();
		mContext = null;
		instance = null;
	}
}
