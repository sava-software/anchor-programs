package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.borsh.Borsh;

public record StakeList() implements Borsh {

  private static final StakeList INSTANCE = new StakeList();
  
  public static StakeList read(final byte[] _data, final int offset) {
    return INSTANCE;
  }
  
  @Override
  public int write(final byte[] _data, final int offset) {
    return 0;
  }
  
  @Override
  public int l() {
    return 0;
  }
}