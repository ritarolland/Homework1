package com.example.homework1.data

class LastKnownRevisionRepository {

    var lastKnownRevision: Int? = 5
        private set

    fun updateRevision(revision: Int) {
        lastKnownRevision = revision
    }
}