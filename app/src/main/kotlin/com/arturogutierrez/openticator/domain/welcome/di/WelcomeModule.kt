package com.arturogutierrez.openticator.domain.welcome.di

import android.content.Context
import com.arturogutierrez.openticator.R
import com.arturogutierrez.openticator.di.PerActivity
import com.arturogutierrez.openticator.domain.cloud.FirebaseSessionManager
import com.arturogutierrez.openticator.domain.cloud.GoogleAuthManager
import com.arturogutierrez.openticator.domain.cloud.SessionManager
import com.arturogutierrez.openticator.domain.welcome.CloudLoginInteractor
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import dagger.Module
import dagger.Provides

@Module
class WelcomeModule {

  @Provides
  fun provideGoogleAuthManager(context: Context): GoogleAuthManager {
    return GoogleAuthManager(context, context.getString(R.string.default_web_client_id))
  }

  @Provides
  @PerActivity
  fun provideCloudLoginInteractor(threadExecutor: ThreadExecutor,
                                  postExecutionThread: PostExecutionThread): CloudLoginInteractor {
    return CloudLoginInteractor(threadExecutor, postExecutionThread)
  }
}
