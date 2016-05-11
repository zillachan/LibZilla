//package zilla.libcore.ui.aspects;
//
//import zilla.libcore.ui.SupportInjectUtil;
//
///**
// * SupportMethodLoading aspect
// */
//aspect SupportMethodLoading {
//    pointcut trackEvent(SupportInjectUtil analyticsTrackerHelper, String category, String action):
//            execution(@me.egorand.cats.analytics.annotations.TrackEvent * *(..)) &&
//                    args(analyticsTrackerHelper, category, action);
//
//    after(SupportInjectUtil analyticsTrackerHelper, String category, String action):
//            trackEvent(analyticsTrackerHelper, category, action) {
//        analyticsTrackerHelper.trackEvent(category, action);
//    }
//}