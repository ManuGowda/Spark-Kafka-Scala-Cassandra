package com.prysm.analytics

import java.util.UUID
import org.joda.time.DateTime
import org.apache.spark.sql.Row
import org.json4s._
import org.json4s.native.JsonParser

object Events {

  /**
   * @param correlation_id Unique identifier for each request, to identify the flow of request
   * @param device_id Unique identifier for each device
   * @param device_root_id Unique identifier for each device's peripherals
   * @param event_name Name of the event / example: cpu_utilization
   * @param event_value Value of the event / example: 80
   * @param device_type Type of the device which generated the event (Mobile/ LPD)
   * @param metadata Additional information related to event
   * @param account_id Unique id for each account (IBM = 2)
   * @param app_version Version of the Application installed (1.0)
   * @param created_at Timestamp at which the event generated
   */
  case class RawEvent(
    correlation_id: String,
    device_id: String,
    device_root_id: String,
    event_name: String,
    event_value: String,
    device_type: String,
    metadata: Map[String, String],
    account_id: Int,
    app_version: Double,
    created_at: String)
}