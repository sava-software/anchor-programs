package software.sava.anchor.programs.glam.protocol.anchor.types;

import software.sava.core.borsh.Borsh;

public enum VoteAuthorizeEnum implements Borsh.Enum {

  Voter,
  Withdrawer;

  public static VoteAuthorizeEnum read(final byte[] _data, final int offset) {
    return Borsh.read(VoteAuthorizeEnum.values(), _data, offset);
  }
}