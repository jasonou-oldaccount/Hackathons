class LetterController < ApplicationController
  
  before_filter :authorize, only: [:new]
  
  def index
  end

  def new
  end
end
