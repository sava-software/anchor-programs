package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public enum PayloadKey implements Borsh.Enum {

  Amount,
  Authority,
  AuthoritySeeds,
  Delegate,
  DelegateSeeds,
  Destination,
  DestinationSeeds,
  Holder,
  Source,
  SourceSeeds;

  public static PayloadKey read(final byte[] _data, final int offset) {
    return Borsh.read(PayloadKey.values(), _data, offset);
  }
}