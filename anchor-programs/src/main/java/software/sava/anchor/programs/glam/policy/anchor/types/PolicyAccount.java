package software.sava.anchor.programs.glam.policy.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record PolicyAccount(PublicKey _address,
                            Discriminator discriminator,
                            PublicKey authority,
                            PublicKey subject,
                            PublicKey mint,
                            PublicKey tokenAccount,
                            long lockedUntilTs) implements Borsh {

  public static final int BYTES = 144;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(218, 201, 183, 164, 156, 127, 81, 175);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int AUTHORITY_OFFSET = 8;
  public static final int SUBJECT_OFFSET = 40;
  public static final int MINT_OFFSET = 72;
  public static final int TOKEN_ACCOUNT_OFFSET = 104;
  public static final int LOCKED_UNTIL_TS_OFFSET = 136;

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createSubjectFilter(final PublicKey subject) {
    return Filter.createMemCompFilter(SUBJECT_OFFSET, subject);
  }

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static Filter createTokenAccountFilter(final PublicKey tokenAccount) {
    return Filter.createMemCompFilter(TOKEN_ACCOUNT_OFFSET, tokenAccount);
  }

  public static Filter createLockedUntilTsFilter(final long lockedUntilTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lockedUntilTs);
    return Filter.createMemCompFilter(LOCKED_UNTIL_TS_OFFSET, _data);
  }

  public static PolicyAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PolicyAccount read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static PolicyAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PolicyAccount> FACTORY = PolicyAccount::read;

  public static PolicyAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var authority = readPubKey(_data, i);
    i += 32;
    final var subject = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var tokenAccount = readPubKey(_data, i);
    i += 32;
    final var lockedUntilTs = getInt64LE(_data, i);
    return new PolicyAccount(_address,
                             discriminator,
                             authority,
                             subject,
                             mint,
                             tokenAccount,
                             lockedUntilTs);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    authority.write(_data, i);
    i += 32;
    subject.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    tokenAccount.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lockedUntilTs);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
