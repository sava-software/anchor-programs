package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record LendingPoolBankCreateEvent(GroupEventHeader header,
                                         PublicKey bank,
                                         PublicKey mint) implements Borsh {

  public static LendingPoolBankCreateEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var header = GroupEventHeader.read(_data, i);
    i += Borsh.len(header);
    final var bank = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    return new LendingPoolBankCreateEvent(header, bank, mint);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(header, _data, i);
    bank.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(header) + 32 + 32;
  }
}
