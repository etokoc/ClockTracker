package com.metoer.clocktracker.ui.application

import android.app.Application
import com.metoer.clocktracker.data.db.ClockDatabase
import com.metoer.clocktracker.data.repositories.ClockRepository
import com.metoer.clocktracker.ui.view.factories.AlarmViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ClockApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ClockApplication))
        bind() from singleton { ClockDatabase(instance()) }
        bind() from singleton { ClockRepository(instance()) }
        bind() from provider {
            AlarmViewModelFactory(instance())
            //AlarmSettingsViewModelFactory(instance())
        }
    }

}
