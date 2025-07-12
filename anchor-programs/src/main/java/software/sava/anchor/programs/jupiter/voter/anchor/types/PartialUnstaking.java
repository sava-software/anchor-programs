package software.sava.anchor.programs.jupiter.voter.anchor.types;

import java.lang.String;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Account to store infor for partial unstaking
public record PartialUnstaking(PublicKey _address,
                               Discriminator discriminator,
                               // The [Escrow] pubkey.
                               PublicKey escrow,
                               // Amount of this partial unstaking
                               long amount,
                               // Timestamp when owner can withdraw the partial unstaking amount
                               long expiration,
                               // buffer for further use
                               BigInteger[] buffers,
                               // Memo
                               String memo, byte[] _memo) implements Borsh {

  public static final int BUFFERS_LEN = 6;
  public static final int ESCROW_OFFSET = 8;
  public static final int AMOUNT_OFFSET = 40;
  public static final int EXPIRATION_OFFSET = 48;
  public static final int BUFFERS_OFFSET = 56;
  public static final int MEMO_OFFSET = 152;

  public static Filter createEscrowFilter(final PublicKey escrow) {
    return Filter.createMemCompFilter(ESCROW_OFFSET, escrow);
  }

  public static Filter createAmountFilter(final long amount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, amount);
    return Filter.createMemCompFilter(AMOUNT_OFFSET, _data);
  }

  public static Filter createExpirationFilter(final long expiration) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, expiration);
    return Filter.createMemCompFilter(EXPIRATION_OFFSET, _data);
  }

  public static PartialUnstaking createRecord(final PublicKey _address,
                                              final Discriminator discriminator,
                                              final PublicKey escrow,
                                              final long amount,
                                              final long expiration,
                                              final BigInteger[] buffers,
                                              final String memo) {
    return new PartialUnstaking(_address,
                                discriminator,
                                escrow,
                                amount,
                                expiration,
                                buffers,
                                memo, memo.getBytes(UTF_8));
  }

  public static PartialUnstaking read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PartialUnstaking read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static PartialUnstaking read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PartialUnstaking> FACTORY = PartialUnstaking::read;

  public static PartialUnstaking read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var escrow = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var expiration = getInt64LE(_data, i);
    i += 8;
    final var buffers = new BigInteger[6];
    i += Borsh.read128Array(buffers, _data, i);
    final var memo = Borsh.string(_data, i);
    return new PartialUnstaking(_address,
                                discriminator,
                                escrow,
                                amount,
                                expiration,
                                buffers,
                                memo, memo.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    escrow.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, expiration);
    i += 8;
    i += Borsh.write128Array(buffers, _data, i);
    i += Borsh.writeVector(_memo, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + 8
         + 8
         + Borsh.len128Array(buffers)
         + Borsh.lenVector(_memo);
  }
}
