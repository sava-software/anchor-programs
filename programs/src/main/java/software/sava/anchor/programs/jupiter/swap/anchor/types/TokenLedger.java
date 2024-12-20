package software.sava.anchor.programs.jupiter.swap.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record TokenLedger(PublicKey _address, Discriminator discriminator, PublicKey tokenAccount, long amount) implements Borsh {

  public static final int BYTES = 48;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(156, 247, 9, 188, 54, 108, 85, 77);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int TOKEN_ACCOUNT_OFFSET = 8;
  public static final int AMOUNT_OFFSET = 40;

  public static Filter createTokenAccountFilter(final PublicKey tokenAccount) {
    return Filter.createMemCompFilter(TOKEN_ACCOUNT_OFFSET, tokenAccount);
  }

  public static Filter createAmountFilter(final long amount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, amount);
    return Filter.createMemCompFilter(AMOUNT_OFFSET, _data);
  }

  public static TokenLedger read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static TokenLedger read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], TokenLedger> FACTORY = TokenLedger::read;

  public static TokenLedger read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var tokenAccount = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    return new TokenLedger(_address, discriminator, tokenAccount, amount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    tokenAccount.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
