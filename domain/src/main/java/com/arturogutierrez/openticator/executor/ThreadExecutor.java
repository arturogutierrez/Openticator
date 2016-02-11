package com.arturogutierrez.openticator.executor;

import java.util.concurrent.Executor;

/**
 * Executor where all job done by interactors will be executed. Always out of the UI thread.
 */
public interface ThreadExecutor extends Executor {

}
