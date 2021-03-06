package com.koleychik.freechat.di.modules

import com.kaleichyk.feature_messages.di.MessagesFeatureApi
import com.kaleichyk.feature_messages.di.MessagesFeatureComponentHolder
import com.kaleichyk.feature_messages.di.MessagesFeatureDependencies
import com.kaleichyk.feature_searching.di.SearchingFeatureApi
import com.kaleichyk.feature_searching.di.SearchingFeatureComponentHolder
import com.kaleichyk.feature_searching.di.SearchingFeatureDependencies
import com.kaleichyk.feature_select_image_impl.di.SelectImageFeatureApi
import com.kaleichyk.feature_select_image_impl.di.SelectImageFeatureComponentHolder
import com.kaleichyk.feature_user_info.di.UserInfoFeatureApi
import com.kaleichyk.feature_user_info.di.UserInfoFeatureComponentHolder
import com.kaleichyk.feature_user_info.di.UserInfoFeatureDependencies
import com.kaleichyk.feature_user_info.di.UserInfoFeatureDestroyer
import com.koleychik.feature_all_dialogs.di.AllDialogsFeatureApi
import com.koleychik.feature_all_dialogs.di.AllDialogsFeatureComponentHolder
import com.koleychik.feature_all_dialogs.di.AllDialogsFeatureDependencies
import com.koleychik.feature_all_dialogs.di.AllDialogsFeatureDestroyer
import com.koleychik.feature_loading_api.LoadingFeatureApi
import com.koleychik.feature_loading_impl.di.LoadingFeatureComponentHolder
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureApi
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureComponentHolder
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureDependencies
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureDestroyer
import com.koleychik.feature_sign.di.SignFeatureApi
import com.koleychik.feature_sign.di.SignFeatureComponentHolder
import com.koleychik.feature_sign.di.SignFeatureDependencies
import com.koleychik.feature_sign.di.SignFeatureDestroyer
import com.koleychik.feature_start.di.StartFeatureApi
import com.koleychik.feature_start.di.StartFeatureComponentHolder
import com.koleychik.feature_start.di.StartFeatureDependencies
import com.koleychik.module_injector.injections.BaseDependencies
import com.koleychik.module_injector.injections.BaseDestroyer
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideMessagesFeatureApi(
        dependencies: MessagesFeatureDependencies,
        destroyer: BaseDestroyer
    ): MessagesFeatureApi {
        MessagesFeatureComponentHolder.init(dependencies, destroyer)
        return MessagesFeatureComponentHolder.get()
    }

    @Provides
    fun provideUserInfoFeatureApi(
        dependencies: UserInfoFeatureDependencies,
        destroyer: UserInfoFeatureDestroyer,
    ): UserInfoFeatureApi {
        UserInfoFeatureComponentHolder.init(dependencies, destroyer)
        return UserInfoFeatureComponentHolder.get()
    }

    @Provides
    fun provideSearchingFeatureApi(
        dependencies: SearchingFeatureDependencies,
        destroyer: BaseDestroyer
    ): SearchingFeatureApi {
        SearchingFeatureComponentHolder.init(dependencies, destroyer)
        return SearchingFeatureComponentHolder.get()
    }

    @Provides
    fun provideAllDialogsFeatureApi(
        dependencies: AllDialogsFeatureDependencies,
        destroyer: AllDialogsFeatureDestroyer
    ): AllDialogsFeatureApi {
        AllDialogsFeatureComponentHolder.init(dependencies, destroyer)
        return AllDialogsFeatureComponentHolder.get()
    }

    @Provides
    fun providePasswordUtilsFeatureApi(
        dependencies: PasswordUtilsFeatureDependencies,
        destroyer: PasswordUtilsFeatureDestroyer
    ): PasswordUtilsFeatureApi {
        PasswordUtilsFeatureComponentHolder.init(dependencies, destroyer)
        return PasswordUtilsFeatureComponentHolder.get()
    }

    @Provides
    fun provideSignFeatureApi(
        dependencies: SignFeatureDependencies,
        destroyer: SignFeatureDestroyer
    ): SignFeatureApi {
        SignFeatureComponentHolder.init(dependencies, destroyer)
        return SignFeatureComponentHolder.get()
    }

    @Provides
    fun provideStartFeatureApi(
        dependencies: StartFeatureDependencies,
        destroyer: BaseDestroyer
    ): StartFeatureApi {
        StartFeatureComponentHolder.init(dependencies, destroyer)
        return StartFeatureComponentHolder.get()
    }

    @Provides
    fun provideLoadingFeatureApi(
        dependencies: BaseDependencies,
        destroyer: BaseDestroyer
    ): LoadingFeatureApi {
        LoadingFeatureComponentHolder.init(dependencies, destroyer)
        return LoadingFeatureComponentHolder.get()
    }

    @Provides
    fun provideSelectImageFeatureApi(
        dependencies: BaseDependencies,
        destroyer: BaseDestroyer
    ): SelectImageFeatureApi {
        SelectImageFeatureComponentHolder.init(dependencies, destroyer)
        return SelectImageFeatureComponentHolder.get()
    }

}