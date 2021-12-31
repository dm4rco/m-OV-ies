/*
Author: Marco D'Aurelio
Purpose: MovieClickListener is a simple custom click listener to be able to tell my app
on which movie was clicked exactly.
*/

package com.daurelio.m_ov_ies

interface MovieClickListener {
    fun onClick(movie: MovieClass)
}